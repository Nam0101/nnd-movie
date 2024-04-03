package com.nndmove.app.web.rest;

import static com.nndmove.app.domain.GenresAsserts.*;
import static com.nndmove.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nndmove.app.IntegrationTest;
import com.nndmove.app.domain.Genres;
import com.nndmove.app.repository.GenresRepository;
import com.nndmove.app.service.dto.GenresDTO;
import com.nndmove.app.service.mapper.GenresMapper;
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
 * Integration tests for the {@link GenresResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GenresResourceIT {

    private static final String DEFAULT_GENRES = "AAAAAAAAAA";
    private static final String UPDATED_GENRES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/genres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GenresRepository genresRepository;

    @Autowired
    private GenresMapper genresMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGenresMockMvc;

    private Genres genres;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Genres createEntity(EntityManager em) {
        Genres genres = new Genres().genres(DEFAULT_GENRES);
        return genres;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Genres createUpdatedEntity(EntityManager em) {
        Genres genres = new Genres().genres(UPDATED_GENRES);
        return genres;
    }

    @BeforeEach
    public void initTest() {
        genres = createEntity(em);
    }

    @Test
    @Transactional
    void createGenres() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Genres
        GenresDTO genresDTO = genresMapper.toDto(genres);
        var returnedGenresDTO = om.readValue(
            restGenresMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(genresDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GenresDTO.class
        );

        // Validate the Genres in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedGenres = genresMapper.toEntity(returnedGenresDTO);
        assertGenresUpdatableFieldsEquals(returnedGenres, getPersistedGenres(returnedGenres));
    }

    @Test
    @Transactional
    void createGenresWithExistingId() throws Exception {
        // Create the Genres with an existing ID
        genres.setId(1L);
        GenresDTO genresDTO = genresMapper.toDto(genres);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGenresMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(genresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Genres in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGenresIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        genres.setGenres(null);

        // Create the Genres, which fails.
        GenresDTO genresDTO = genresMapper.toDto(genres);

        restGenresMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(genresDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGenres() throws Exception {
        // Initialize the database
        genresRepository.saveAndFlush(genres);

        // Get all the genresList
        restGenresMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genres.getId().intValue())))
            .andExpect(jsonPath("$.[*].genres").value(hasItem(DEFAULT_GENRES)));
    }

    @Test
    @Transactional
    void getGenres() throws Exception {
        // Initialize the database
        genresRepository.saveAndFlush(genres);

        // Get the genres
        restGenresMockMvc
            .perform(get(ENTITY_API_URL_ID, genres.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(genres.getId().intValue()))
            .andExpect(jsonPath("$.genres").value(DEFAULT_GENRES));
    }

    @Test
    @Transactional
    void getNonExistingGenres() throws Exception {
        // Get the genres
        restGenresMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGenres() throws Exception {
        // Initialize the database
        genresRepository.saveAndFlush(genres);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the genres
        Genres updatedGenres = genresRepository.findById(genres.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGenres are not directly saved in db
        em.detach(updatedGenres);
        updatedGenres.genres(UPDATED_GENRES);
        GenresDTO genresDTO = genresMapper.toDto(updatedGenres);

        restGenresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, genresDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(genresDTO))
            )
            .andExpect(status().isOk());

        // Validate the Genres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGenresToMatchAllProperties(updatedGenres);
    }

    @Test
    @Transactional
    void putNonExistingGenres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        genres.setId(longCount.incrementAndGet());

        // Create the Genres
        GenresDTO genresDTO = genresMapper.toDto(genres);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGenresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, genresDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(genresDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Genres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGenres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        genres.setId(longCount.incrementAndGet());

        // Create the Genres
        GenresDTO genresDTO = genresMapper.toDto(genres);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(genresDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Genres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGenres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        genres.setId(longCount.incrementAndGet());

        // Create the Genres
        GenresDTO genresDTO = genresMapper.toDto(genres);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenresMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(genresDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Genres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGenresWithPatch() throws Exception {
        // Initialize the database
        genresRepository.saveAndFlush(genres);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the genres using partial update
        Genres partialUpdatedGenres = new Genres();
        partialUpdatedGenres.setId(genres.getId());

        partialUpdatedGenres.genres(UPDATED_GENRES);

        restGenresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGenres.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGenres))
            )
            .andExpect(status().isOk());

        // Validate the Genres in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGenresUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedGenres, genres), getPersistedGenres(genres));
    }

    @Test
    @Transactional
    void fullUpdateGenresWithPatch() throws Exception {
        // Initialize the database
        genresRepository.saveAndFlush(genres);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the genres using partial update
        Genres partialUpdatedGenres = new Genres();
        partialUpdatedGenres.setId(genres.getId());

        partialUpdatedGenres.genres(UPDATED_GENRES);

        restGenresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGenres.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGenres))
            )
            .andExpect(status().isOk());

        // Validate the Genres in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGenresUpdatableFieldsEquals(partialUpdatedGenres, getPersistedGenres(partialUpdatedGenres));
    }

    @Test
    @Transactional
    void patchNonExistingGenres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        genres.setId(longCount.incrementAndGet());

        // Create the Genres
        GenresDTO genresDTO = genresMapper.toDto(genres);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGenresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, genresDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(genresDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Genres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGenres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        genres.setId(longCount.incrementAndGet());

        // Create the Genres
        GenresDTO genresDTO = genresMapper.toDto(genres);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(genresDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Genres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGenres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        genres.setId(longCount.incrementAndGet());

        // Create the Genres
        GenresDTO genresDTO = genresMapper.toDto(genres);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenresMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(genresDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Genres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGenres() throws Exception {
        // Initialize the database
        genresRepository.saveAndFlush(genres);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the genres
        restGenresMockMvc
            .perform(delete(ENTITY_API_URL_ID, genres.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return genresRepository.count();
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

    protected Genres getPersistedGenres(Genres genres) {
        return genresRepository.findById(genres.getId()).orElseThrow();
    }

    protected void assertPersistedGenresToMatchAllProperties(Genres expectedGenres) {
        assertGenresAllPropertiesEquals(expectedGenres, getPersistedGenres(expectedGenres));
    }

    protected void assertPersistedGenresToMatchUpdatableProperties(Genres expectedGenres) {
        assertGenresAllUpdatablePropertiesEquals(expectedGenres, getPersistedGenres(expectedGenres));
    }
}
