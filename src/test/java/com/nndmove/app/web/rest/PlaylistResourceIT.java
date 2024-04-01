package com.nndmove.app.web.rest;

import static com.nndmove.app.domain.PlaylistAsserts.*;
import static com.nndmove.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nndmove.app.IntegrationTest;
import com.nndmove.app.domain.Playlist;
import com.nndmove.app.repository.PlaylistRepository;
import com.nndmove.app.service.dto.PlaylistDTO;
import com.nndmove.app.service.mapper.PlaylistMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PlaylistResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlaylistResourceIT {

    private static final String ENTITY_API_URL = "/api/playlists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private PlaylistMapper playlistMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlaylistMockMvc;

    private Playlist playlist;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Playlist createEntity(EntityManager em) {
        Playlist playlist = new Playlist();
        return playlist;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Playlist createUpdatedEntity(EntityManager em) {
        Playlist playlist = new Playlist();
        return playlist;
    }

    @BeforeEach
    public void initTest() {
        playlist = createEntity(em);
    }

    @Test
    @Transactional
    void createPlaylist() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Playlist
        PlaylistDTO playlistDTO = playlistMapper.toDto(playlist);
        var returnedPlaylistDTO = om.readValue(
            restPlaylistMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(playlistDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PlaylistDTO.class
        );

        // Validate the Playlist in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPlaylist = playlistMapper.toEntity(returnedPlaylistDTO);
        assertPlaylistUpdatableFieldsEquals(returnedPlaylist, getPersistedPlaylist(returnedPlaylist));
    }

    @Test
    @Transactional
    void createPlaylistWithExistingId() throws Exception {
        // Create the Playlist with an existing ID
        playlist.setId(1L);
        PlaylistDTO playlistDTO = playlistMapper.toDto(playlist);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaylistMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(playlistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Playlist in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlaylists() throws Exception {
        // Initialize the database
        playlistRepository.saveAndFlush(playlist);

        // Get all the playlistList
        restPlaylistMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playlist.getId().intValue())));
    }

    @Test
    @Transactional
    void getPlaylist() throws Exception {
        // Initialize the database
        playlistRepository.saveAndFlush(playlist);

        // Get the playlist
        restPlaylistMockMvc
            .perform(get(ENTITY_API_URL_ID, playlist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(playlist.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPlaylist() throws Exception {
        // Get the playlist
        restPlaylistMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlaylist() throws Exception {
        // Initialize the database
        playlistRepository.saveAndFlush(playlist);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the playlist
        Playlist updatedPlaylist = playlistRepository.findById(playlist.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPlaylist are not directly saved in db
        em.detach(updatedPlaylist);
        PlaylistDTO playlistDTO = playlistMapper.toDto(updatedPlaylist);

        restPlaylistMockMvc
            .perform(
                put(ENTITY_API_URL_ID, playlistDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(playlistDTO))
            )
            .andExpect(status().isOk());

        // Validate the Playlist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPlaylistToMatchAllProperties(updatedPlaylist);
    }

    @Test
    @Transactional
    void putNonExistingPlaylist() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        playlist.setId(longCount.incrementAndGet());

        // Create the Playlist
        PlaylistDTO playlistDTO = playlistMapper.toDto(playlist);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaylistMockMvc
            .perform(
                put(ENTITY_API_URL_ID, playlistDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(playlistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Playlist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlaylist() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        playlist.setId(longCount.incrementAndGet());

        // Create the Playlist
        PlaylistDTO playlistDTO = playlistMapper.toDto(playlist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaylistMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(playlistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Playlist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlaylist() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        playlist.setId(longCount.incrementAndGet());

        // Create the Playlist
        PlaylistDTO playlistDTO = playlistMapper.toDto(playlist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaylistMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(playlistDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Playlist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlaylistWithPatch() throws Exception {
        // Initialize the database
        playlistRepository.saveAndFlush(playlist);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the playlist using partial update
        Playlist partialUpdatedPlaylist = new Playlist();
        partialUpdatedPlaylist.setId(playlist.getId());

        restPlaylistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlaylist.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlaylist))
            )
            .andExpect(status().isOk());

        // Validate the Playlist in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlaylistUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPlaylist, playlist), getPersistedPlaylist(playlist));
    }

    @Test
    @Transactional
    void fullUpdatePlaylistWithPatch() throws Exception {
        // Initialize the database
        playlistRepository.saveAndFlush(playlist);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the playlist using partial update
        Playlist partialUpdatedPlaylist = new Playlist();
        partialUpdatedPlaylist.setId(playlist.getId());

        restPlaylistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlaylist.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlaylist))
            )
            .andExpect(status().isOk());

        // Validate the Playlist in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlaylistUpdatableFieldsEquals(partialUpdatedPlaylist, getPersistedPlaylist(partialUpdatedPlaylist));
    }

    @Test
    @Transactional
    void patchNonExistingPlaylist() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        playlist.setId(longCount.incrementAndGet());

        // Create the Playlist
        PlaylistDTO playlistDTO = playlistMapper.toDto(playlist);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaylistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, playlistDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(playlistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Playlist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlaylist() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        playlist.setId(longCount.incrementAndGet());

        // Create the Playlist
        PlaylistDTO playlistDTO = playlistMapper.toDto(playlist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaylistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(playlistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Playlist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlaylist() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        playlist.setId(longCount.incrementAndGet());

        // Create the Playlist
        PlaylistDTO playlistDTO = playlistMapper.toDto(playlist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaylistMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(playlistDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Playlist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlaylist() throws Exception {
        // Initialize the database
        playlistRepository.saveAndFlush(playlist);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the playlist
        restPlaylistMockMvc
            .perform(delete(ENTITY_API_URL_ID, playlist.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return playlistRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Playlist getPersistedPlaylist(Playlist playlist) {
        return playlistRepository.findById(playlist.getId()).orElseThrow();
    }

    protected void assertPersistedPlaylistToMatchAllProperties(Playlist expectedPlaylist) {
        assertPlaylistAllPropertiesEquals(expectedPlaylist, getPersistedPlaylist(expectedPlaylist));
    }

    protected void assertPersistedPlaylistToMatchUpdatableProperties(Playlist expectedPlaylist) {
        assertPlaylistAllUpdatablePropertiesEquals(expectedPlaylist, getPersistedPlaylist(expectedPlaylist));
    }
}
