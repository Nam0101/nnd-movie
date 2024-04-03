package com.nndmove.app.web.rest;

import static com.nndmove.app.domain.MovieResourceAsserts.*;
import static com.nndmove.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nndmove.app.IntegrationTest;
import com.nndmove.app.domain.MovieResource;
import com.nndmove.app.repository.MovieResourceRepository;
import com.nndmove.app.service.dto.MovieResourceDTO;
import com.nndmove.app.service.mapper.MovieResourceMapper;
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
 * Integration tests for the {@link MovieResourceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MovieResourceResourceIT {

    private static final Integer DEFAULT_PART = 1;
    private static final Integer UPDATED_PART = 2;

    private static final String DEFAULT_LINK_EMBED = "AAAAAAAAAA";
    private static final String UPDATED_LINK_EMBED = "BBBBBBBBBB";

    private static final String DEFAULT_LINK_M_3_U_8 = "AAAAAAAAAA";
    private static final String UPDATED_LINK_M_3_U_8 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/movie-resources";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MovieResourceRepository movieResourceRepository;

    @Autowired
    private MovieResourceMapper movieResourceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMovieResourceMockMvc;

    private MovieResource movieResource;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieResource createEntity(EntityManager em) {
        MovieResource movieResource = new MovieResource().part(DEFAULT_PART).linkEmbed(DEFAULT_LINK_EMBED).linkM3u8(DEFAULT_LINK_M_3_U_8);
        return movieResource;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieResource createUpdatedEntity(EntityManager em) {
        MovieResource movieResource = new MovieResource().part(UPDATED_PART).linkEmbed(UPDATED_LINK_EMBED).linkM3u8(UPDATED_LINK_M_3_U_8);
        return movieResource;
    }

    @BeforeEach
    public void initTest() {
        movieResource = createEntity(em);
    }

    @Test
    @Transactional
    void createMovieResource() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MovieResource
        MovieResourceDTO movieResourceDTO = movieResourceMapper.toDto(movieResource);
        var returnedMovieResourceDTO = om.readValue(
            restMovieResourceMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieResourceDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MovieResourceDTO.class
        );

        // Validate the MovieResource in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMovieResource = movieResourceMapper.toEntity(returnedMovieResourceDTO);
        assertMovieResourceUpdatableFieldsEquals(returnedMovieResource, getPersistedMovieResource(returnedMovieResource));
    }

    @Test
    @Transactional
    void createMovieResourceWithExistingId() throws Exception {
        // Create the MovieResource with an existing ID
        movieResource.setId(1L);
        MovieResourceDTO movieResourceDTO = movieResourceMapper.toDto(movieResource);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieResourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieResourceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieResource in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPartIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        movieResource.setPart(null);

        // Create the MovieResource, which fails.
        MovieResourceDTO movieResourceDTO = movieResourceMapper.toDto(movieResource);

        restMovieResourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieResourceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLinkEmbedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        movieResource.setLinkEmbed(null);

        // Create the MovieResource, which fails.
        MovieResourceDTO movieResourceDTO = movieResourceMapper.toDto(movieResource);

        restMovieResourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieResourceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLinkM3u8IsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        movieResource.setLinkM3u8(null);

        // Create the MovieResource, which fails.
        MovieResourceDTO movieResourceDTO = movieResourceMapper.toDto(movieResource);

        restMovieResourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieResourceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMovieResources() throws Exception {
        // Initialize the database
        movieResourceRepository.saveAndFlush(movieResource);

        // Get all the movieResourceList
        restMovieResourceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieResource.getId().intValue())))
            .andExpect(jsonPath("$.[*].part").value(hasItem(DEFAULT_PART)))
            .andExpect(jsonPath("$.[*].linkEmbed").value(hasItem(DEFAULT_LINK_EMBED)))
            .andExpect(jsonPath("$.[*].linkM3u8").value(hasItem(DEFAULT_LINK_M_3_U_8)));
    }

    @Test
    @Transactional
    void getMovieResource() throws Exception {
        // Initialize the database
        movieResourceRepository.saveAndFlush(movieResource);

        // Get the movieResource
        restMovieResourceMockMvc
            .perform(get(ENTITY_API_URL_ID, movieResource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movieResource.getId().intValue()))
            .andExpect(jsonPath("$.part").value(DEFAULT_PART))
            .andExpect(jsonPath("$.linkEmbed").value(DEFAULT_LINK_EMBED))
            .andExpect(jsonPath("$.linkM3u8").value(DEFAULT_LINK_M_3_U_8));
    }

    @Test
    @Transactional
    void getNonExistingMovieResource() throws Exception {
        // Get the movieResource
        restMovieResourceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMovieResource() throws Exception {
        // Initialize the database
        movieResourceRepository.saveAndFlush(movieResource);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the movieResource
        MovieResource updatedMovieResource = movieResourceRepository.findById(movieResource.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMovieResource are not directly saved in db
        em.detach(updatedMovieResource);
        updatedMovieResource.part(UPDATED_PART).linkEmbed(UPDATED_LINK_EMBED).linkM3u8(UPDATED_LINK_M_3_U_8);
        MovieResourceDTO movieResourceDTO = movieResourceMapper.toDto(updatedMovieResource);

        restMovieResourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, movieResourceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(movieResourceDTO))
            )
            .andExpect(status().isOk());

        // Validate the MovieResource in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMovieResourceToMatchAllProperties(updatedMovieResource);
    }

    @Test
    @Transactional
    void putNonExistingMovieResource() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movieResource.setId(longCount.incrementAndGet());

        // Create the MovieResource
        MovieResourceDTO movieResourceDTO = movieResourceMapper.toDto(movieResource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieResourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, movieResourceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(movieResourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MovieResource in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMovieResource() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movieResource.setId(longCount.incrementAndGet());

        // Create the MovieResource
        MovieResourceDTO movieResourceDTO = movieResourceMapper.toDto(movieResource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieResourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(movieResourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MovieResource in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMovieResource() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movieResource.setId(longCount.incrementAndGet());

        // Create the MovieResource
        MovieResourceDTO movieResourceDTO = movieResourceMapper.toDto(movieResource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieResourceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieResourceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MovieResource in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMovieResourceWithPatch() throws Exception {
        // Initialize the database
        movieResourceRepository.saveAndFlush(movieResource);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the movieResource using partial update
        MovieResource partialUpdatedMovieResource = new MovieResource();
        partialUpdatedMovieResource.setId(movieResource.getId());

        partialUpdatedMovieResource.part(UPDATED_PART).linkM3u8(UPDATED_LINK_M_3_U_8);

        restMovieResourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMovieResource.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMovieResource))
            )
            .andExpect(status().isOk());

        // Validate the MovieResource in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMovieResourceUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMovieResource, movieResource),
            getPersistedMovieResource(movieResource)
        );
    }

    @Test
    @Transactional
    void fullUpdateMovieResourceWithPatch() throws Exception {
        // Initialize the database
        movieResourceRepository.saveAndFlush(movieResource);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the movieResource using partial update
        MovieResource partialUpdatedMovieResource = new MovieResource();
        partialUpdatedMovieResource.setId(movieResource.getId());

        partialUpdatedMovieResource.part(UPDATED_PART).linkEmbed(UPDATED_LINK_EMBED).linkM3u8(UPDATED_LINK_M_3_U_8);

        restMovieResourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMovieResource.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMovieResource))
            )
            .andExpect(status().isOk());

        // Validate the MovieResource in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMovieResourceUpdatableFieldsEquals(partialUpdatedMovieResource, getPersistedMovieResource(partialUpdatedMovieResource));
    }

    @Test
    @Transactional
    void patchNonExistingMovieResource() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movieResource.setId(longCount.incrementAndGet());

        // Create the MovieResource
        MovieResourceDTO movieResourceDTO = movieResourceMapper.toDto(movieResource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieResourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, movieResourceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(movieResourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MovieResource in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMovieResource() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movieResource.setId(longCount.incrementAndGet());

        // Create the MovieResource
        MovieResourceDTO movieResourceDTO = movieResourceMapper.toDto(movieResource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieResourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(movieResourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MovieResource in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMovieResource() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movieResource.setId(longCount.incrementAndGet());

        // Create the MovieResource
        MovieResourceDTO movieResourceDTO = movieResourceMapper.toDto(movieResource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieResourceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(movieResourceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MovieResource in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMovieResource() throws Exception {
        // Initialize the database
        movieResourceRepository.saveAndFlush(movieResource);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the movieResource
        restMovieResourceMockMvc
            .perform(delete(ENTITY_API_URL_ID, movieResource.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return movieResourceRepository.count();
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

    protected MovieResource getPersistedMovieResource(MovieResource movieResource) {
        return movieResourceRepository.findById(movieResource.getId()).orElseThrow();
    }

    protected void assertPersistedMovieResourceToMatchAllProperties(MovieResource expectedMovieResource) {
        assertMovieResourceAllPropertiesEquals(expectedMovieResource, getPersistedMovieResource(expectedMovieResource));
    }

    protected void assertPersistedMovieResourceToMatchUpdatableProperties(MovieResource expectedMovieResource) {
        assertMovieResourceAllUpdatablePropertiesEquals(expectedMovieResource, getPersistedMovieResource(expectedMovieResource));
    }
}
