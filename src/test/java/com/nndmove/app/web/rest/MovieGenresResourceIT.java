package com.nndmove.app.web.rest;

import static com.nndmove.app.domain.MovieGenresAsserts.*;
import static com.nndmove.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nndmove.app.IntegrationTest;
import com.nndmove.app.domain.MovieGenres;
import com.nndmove.app.repository.MovieGenresRepository;
import com.nndmove.app.service.dto.MovieGenresDTO;
import com.nndmove.app.service.mapper.MovieGenresMapper;
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
 * Integration tests for the {@link MovieGenresResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MovieGenresResourceIT {

    private static final String ENTITY_API_URL = "/api/movie-genres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MovieGenresRepository movieGenresRepository;

    @Autowired
    private MovieGenresMapper movieGenresMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMovieGenresMockMvc;

    private MovieGenres movieGenres;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieGenres createEntity(EntityManager em) {
        MovieGenres movieGenres = new MovieGenres();
        return movieGenres;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieGenres createUpdatedEntity(EntityManager em) {
        MovieGenres movieGenres = new MovieGenres();
        return movieGenres;
    }

    @BeforeEach
    public void initTest() {
        movieGenres = createEntity(em);
    }

    @Test
    @Transactional
    void createMovieGenres() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MovieGenres
        MovieGenresDTO movieGenresDTO = movieGenresMapper.toDto(movieGenres);
        var returnedMovieGenresDTO = om.readValue(
            restMovieGenresMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieGenresDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MovieGenresDTO.class
        );

        // Validate the MovieGenres in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMovieGenres = movieGenresMapper.toEntity(returnedMovieGenresDTO);
        assertMovieGenresUpdatableFieldsEquals(returnedMovieGenres, getPersistedMovieGenres(returnedMovieGenres));
    }

    @Test
    @Transactional
    void createMovieGenresWithExistingId() throws Exception {
        // Create the MovieGenres with an existing ID
        movieGenres.setId(1L);
        MovieGenresDTO movieGenresDTO = movieGenresMapper.toDto(movieGenres);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieGenresMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieGenresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieGenres in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMovieGenres() throws Exception {
        // Initialize the database
        movieGenresRepository.saveAndFlush(movieGenres);

        // Get all the movieGenresList
        restMovieGenresMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieGenres.getId().intValue())));
    }

    @Test
    @Transactional
    void getMovieGenres() throws Exception {
        // Initialize the database
        movieGenresRepository.saveAndFlush(movieGenres);

        // Get the movieGenres
        restMovieGenresMockMvc
            .perform(get(ENTITY_API_URL_ID, movieGenres.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movieGenres.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingMovieGenres() throws Exception {
        // Get the movieGenres
        restMovieGenresMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMovieGenres() throws Exception {
        // Initialize the database
        movieGenresRepository.saveAndFlush(movieGenres);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the movieGenres
        MovieGenres updatedMovieGenres = movieGenresRepository.findById(movieGenres.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMovieGenres are not directly saved in db
        em.detach(updatedMovieGenres);
        MovieGenresDTO movieGenresDTO = movieGenresMapper.toDto(updatedMovieGenres);

        restMovieGenresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, movieGenresDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(movieGenresDTO))
            )
            .andExpect(status().isOk());

        // Validate the MovieGenres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMovieGenresToMatchAllProperties(updatedMovieGenres);
    }

    @Test
    @Transactional
    void putNonExistingMovieGenres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movieGenres.setId(longCount.incrementAndGet());

        // Create the MovieGenres
        MovieGenresDTO movieGenresDTO = movieGenresMapper.toDto(movieGenres);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieGenresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, movieGenresDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(movieGenresDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MovieGenres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMovieGenres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movieGenres.setId(longCount.incrementAndGet());

        // Create the MovieGenres
        MovieGenresDTO movieGenresDTO = movieGenresMapper.toDto(movieGenres);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieGenresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(movieGenresDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MovieGenres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMovieGenres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movieGenres.setId(longCount.incrementAndGet());

        // Create the MovieGenres
        MovieGenresDTO movieGenresDTO = movieGenresMapper.toDto(movieGenres);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieGenresMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieGenresDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MovieGenres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMovieGenresWithPatch() throws Exception {
        // Initialize the database
        movieGenresRepository.saveAndFlush(movieGenres);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the movieGenres using partial update
        MovieGenres partialUpdatedMovieGenres = new MovieGenres();
        partialUpdatedMovieGenres.setId(movieGenres.getId());

        restMovieGenresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMovieGenres.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMovieGenres))
            )
            .andExpect(status().isOk());

        // Validate the MovieGenres in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMovieGenresUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMovieGenres, movieGenres),
            getPersistedMovieGenres(movieGenres)
        );
    }

    @Test
    @Transactional
    void fullUpdateMovieGenresWithPatch() throws Exception {
        // Initialize the database
        movieGenresRepository.saveAndFlush(movieGenres);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the movieGenres using partial update
        MovieGenres partialUpdatedMovieGenres = new MovieGenres();
        partialUpdatedMovieGenres.setId(movieGenres.getId());

        restMovieGenresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMovieGenres.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMovieGenres))
            )
            .andExpect(status().isOk());

        // Validate the MovieGenres in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMovieGenresUpdatableFieldsEquals(partialUpdatedMovieGenres, getPersistedMovieGenres(partialUpdatedMovieGenres));
    }

    @Test
    @Transactional
    void patchNonExistingMovieGenres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movieGenres.setId(longCount.incrementAndGet());

        // Create the MovieGenres
        MovieGenresDTO movieGenresDTO = movieGenresMapper.toDto(movieGenres);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieGenresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, movieGenresDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(movieGenresDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MovieGenres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMovieGenres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movieGenres.setId(longCount.incrementAndGet());

        // Create the MovieGenres
        MovieGenresDTO movieGenresDTO = movieGenresMapper.toDto(movieGenres);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieGenresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(movieGenresDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MovieGenres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMovieGenres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movieGenres.setId(longCount.incrementAndGet());

        // Create the MovieGenres
        MovieGenresDTO movieGenresDTO = movieGenresMapper.toDto(movieGenres);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieGenresMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(movieGenresDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MovieGenres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMovieGenres() throws Exception {
        // Initialize the database
        movieGenresRepository.saveAndFlush(movieGenres);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the movieGenres
        restMovieGenresMockMvc
            .perform(delete(ENTITY_API_URL_ID, movieGenres.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return movieGenresRepository.count();
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

    protected MovieGenres getPersistedMovieGenres(MovieGenres movieGenres) {
        return movieGenresRepository.findById(movieGenres.getId()).orElseThrow();
    }

    protected void assertPersistedMovieGenresToMatchAllProperties(MovieGenres expectedMovieGenres) {
        assertMovieGenresAllPropertiesEquals(expectedMovieGenres, getPersistedMovieGenres(expectedMovieGenres));
    }

    protected void assertPersistedMovieGenresToMatchUpdatableProperties(MovieGenres expectedMovieGenres) {
        assertMovieGenresAllUpdatablePropertiesEquals(expectedMovieGenres, getPersistedMovieGenres(expectedMovieGenres));
    }
}
