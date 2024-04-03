package com.nndmove.app.web.rest;

import static com.nndmove.app.domain.MovieAsserts.*;
import static com.nndmove.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nndmove.app.IntegrationTest;
import com.nndmove.app.domain.Movie;
import com.nndmove.app.repository.MovieRepository;
import com.nndmove.app.service.MovieService;
import com.nndmove.app.service.dto.MovieDTO;
import com.nndmove.app.service.mapper.MovieMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MovieResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MovieResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGIN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_COMPLETED = false;
    private static final Boolean UPDATED_IS_COMPLETED = true;

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    private static final String DEFAULT_EPISODE_CURRENT = "AAAAAAAAAA";
    private static final String UPDATED_EPISODE_CURRENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_EPISODE_TOTAL = 1;
    private static final Integer UPDATED_EPISODE_TOTAL = 2;

    private static final String DEFAULT_QUALITY = "AAAAAAAAAA";
    private static final String UPDATED_QUALITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final String DEFAULT_TRAILER_URL = "AAAAAAAAAA";
    private static final String UPDATED_TRAILER_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SINGLE = false;
    private static final Boolean UPDATED_IS_SINGLE = true;

    private static final String DEFAULT_THUMB_URL = "AAAAAAAAAA";
    private static final String UPDATED_THUMB_URL = "BBBBBBBBBB";

    private static final String DEFAULT_POSTER_URL = "AAAAAAAAAA";
    private static final String UPDATED_POSTER_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ACTORS = "AAAAAAAAAA";
    private static final String UPDATED_ACTORS = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PREMIUM_ONLY = false;
    private static final Boolean UPDATED_PREMIUM_ONLY = true;

    private static final String ENTITY_API_URL = "/api/movies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MovieRepository movieRepository;

    @Mock
    private MovieRepository movieRepositoryMock;

    @Autowired
    private MovieMapper movieMapper;

    @Mock
    private MovieService movieServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMovieMockMvc;

    private Movie movie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movie createEntity(EntityManager em) {
        Movie movie = new Movie()
            .name(DEFAULT_NAME)
            .originName(DEFAULT_ORIGIN_NAME)
            .isCompleted(DEFAULT_IS_COMPLETED)
            .slug(DEFAULT_SLUG)
            .episodeCurrent(DEFAULT_EPISODE_CURRENT)
            .episodeTotal(DEFAULT_EPISODE_TOTAL)
            .quality(DEFAULT_QUALITY)
            .year(DEFAULT_YEAR)
            .trailerUrl(DEFAULT_TRAILER_URL)
            .time(DEFAULT_TIME)
            .content(DEFAULT_CONTENT)
            .isSingle(DEFAULT_IS_SINGLE)
            .thumbUrl(DEFAULT_THUMB_URL)
            .posterUrl(DEFAULT_POSTER_URL)
            .actors(DEFAULT_ACTORS)
            .country(DEFAULT_COUNTRY)
            .premiumOnly(DEFAULT_PREMIUM_ONLY);
        return movie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movie createUpdatedEntity(EntityManager em) {
        Movie movie = new Movie()
            .name(UPDATED_NAME)
            .originName(UPDATED_ORIGIN_NAME)
            .isCompleted(UPDATED_IS_COMPLETED)
            .slug(UPDATED_SLUG)
            .episodeCurrent(UPDATED_EPISODE_CURRENT)
            .episodeTotal(UPDATED_EPISODE_TOTAL)
            .quality(UPDATED_QUALITY)
            .year(UPDATED_YEAR)
            .trailerUrl(UPDATED_TRAILER_URL)
            .time(UPDATED_TIME)
            .content(UPDATED_CONTENT)
            .isSingle(UPDATED_IS_SINGLE)
            .thumbUrl(UPDATED_THUMB_URL)
            .posterUrl(UPDATED_POSTER_URL)
            .actors(UPDATED_ACTORS)
            .country(UPDATED_COUNTRY)
            .premiumOnly(UPDATED_PREMIUM_ONLY);
        return movie;
    }

    @BeforeEach
    public void initTest() {
        movie = createEntity(em);
    }

    @Test
    @Transactional
    void createMovie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Movie
        MovieDTO movieDTO = movieMapper.toDto(movie);
        var returnedMovieDTO = om.readValue(
            restMovieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MovieDTO.class
        );

        // Validate the Movie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMovie = movieMapper.toEntity(returnedMovieDTO);
        assertMovieUpdatableFieldsEquals(returnedMovie, getPersistedMovie(returnedMovie));
    }

    @Test
    @Transactional
    void createMovieWithExistingId() throws Exception {
        // Create the Movie with an existing ID
        movie.setId(1L);
        MovieDTO movieDTO = movieMapper.toDto(movie);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        movie.setName(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOriginNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        movie.setOriginName(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsCompletedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        movie.setIsCompleted(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSlugIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        movie.setSlug(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEpisodeCurrentIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        movie.setEpisodeCurrent(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEpisodeTotalIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        movie.setEpisodeTotal(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQualityIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        movie.setQuality(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkYearIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        movie.setYear(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsSingleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        movie.setIsSingle(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPremiumOnlyIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        movie.setPremiumOnly(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMovies() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList
        restMovieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movie.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].originName").value(hasItem(DEFAULT_ORIGIN_NAME)))
            .andExpect(jsonPath("$.[*].isCompleted").value(hasItem(DEFAULT_IS_COMPLETED.booleanValue())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)))
            .andExpect(jsonPath("$.[*].episodeCurrent").value(hasItem(DEFAULT_EPISODE_CURRENT)))
            .andExpect(jsonPath("$.[*].episodeTotal").value(hasItem(DEFAULT_EPISODE_TOTAL)))
            .andExpect(jsonPath("$.[*].quality").value(hasItem(DEFAULT_QUALITY)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].trailerUrl").value(hasItem(DEFAULT_TRAILER_URL)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].isSingle").value(hasItem(DEFAULT_IS_SINGLE.booleanValue())))
            .andExpect(jsonPath("$.[*].thumbUrl").value(hasItem(DEFAULT_THUMB_URL)))
            .andExpect(jsonPath("$.[*].posterUrl").value(hasItem(DEFAULT_POSTER_URL)))
            .andExpect(jsonPath("$.[*].actors").value(hasItem(DEFAULT_ACTORS)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].premiumOnly").value(hasItem(DEFAULT_PREMIUM_ONLY.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMoviesWithEagerRelationshipsIsEnabled() throws Exception {
        when(movieServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMovieMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(movieServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMoviesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(movieServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMovieMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(movieRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMovie() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get the movie
        restMovieMockMvc
            .perform(get(ENTITY_API_URL_ID, movie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movie.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.originName").value(DEFAULT_ORIGIN_NAME))
            .andExpect(jsonPath("$.isCompleted").value(DEFAULT_IS_COMPLETED.booleanValue()))
            .andExpect(jsonPath("$.slug").value(DEFAULT_SLUG))
            .andExpect(jsonPath("$.episodeCurrent").value(DEFAULT_EPISODE_CURRENT))
            .andExpect(jsonPath("$.episodeTotal").value(DEFAULT_EPISODE_TOTAL))
            .andExpect(jsonPath("$.quality").value(DEFAULT_QUALITY))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.trailerUrl").value(DEFAULT_TRAILER_URL))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.isSingle").value(DEFAULT_IS_SINGLE.booleanValue()))
            .andExpect(jsonPath("$.thumbUrl").value(DEFAULT_THUMB_URL))
            .andExpect(jsonPath("$.posterUrl").value(DEFAULT_POSTER_URL))
            .andExpect(jsonPath("$.actors").value(DEFAULT_ACTORS))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.premiumOnly").value(DEFAULT_PREMIUM_ONLY.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingMovie() throws Exception {
        // Get the movie
        restMovieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMovie() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the movie
        Movie updatedMovie = movieRepository.findById(movie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMovie are not directly saved in db
        em.detach(updatedMovie);
        updatedMovie
            .name(UPDATED_NAME)
            .originName(UPDATED_ORIGIN_NAME)
            .isCompleted(UPDATED_IS_COMPLETED)
            .slug(UPDATED_SLUG)
            .episodeCurrent(UPDATED_EPISODE_CURRENT)
            .episodeTotal(UPDATED_EPISODE_TOTAL)
            .quality(UPDATED_QUALITY)
            .year(UPDATED_YEAR)
            .trailerUrl(UPDATED_TRAILER_URL)
            .time(UPDATED_TIME)
            .content(UPDATED_CONTENT)
            .isSingle(UPDATED_IS_SINGLE)
            .thumbUrl(UPDATED_THUMB_URL)
            .posterUrl(UPDATED_POSTER_URL)
            .actors(UPDATED_ACTORS)
            .country(UPDATED_COUNTRY)
            .premiumOnly(UPDATED_PREMIUM_ONLY);
        MovieDTO movieDTO = movieMapper.toDto(updatedMovie);

        restMovieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, movieDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieDTO))
            )
            .andExpect(status().isOk());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMovieToMatchAllProperties(updatedMovie);
    }

    @Test
    @Transactional
    void putNonExistingMovie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movie.setId(longCount.incrementAndGet());

        // Create the Movie
        MovieDTO movieDTO = movieMapper.toDto(movie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, movieDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMovie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movie.setId(longCount.incrementAndGet());

        // Create the Movie
        MovieDTO movieDTO = movieMapper.toDto(movie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(movieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMovie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movie.setId(longCount.incrementAndGet());

        // Create the Movie
        MovieDTO movieDTO = movieMapper.toDto(movie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movieDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMovieWithPatch() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the movie using partial update
        Movie partialUpdatedMovie = new Movie();
        partialUpdatedMovie.setId(movie.getId());

        partialUpdatedMovie
            .name(UPDATED_NAME)
            .originName(UPDATED_ORIGIN_NAME)
            .isCompleted(UPDATED_IS_COMPLETED)
            .episodeCurrent(UPDATED_EPISODE_CURRENT)
            .episodeTotal(UPDATED_EPISODE_TOTAL)
            .year(UPDATED_YEAR)
            .trailerUrl(UPDATED_TRAILER_URL)
            .thumbUrl(UPDATED_THUMB_URL)
            .posterUrl(UPDATED_POSTER_URL)
            .country(UPDATED_COUNTRY);

        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMovie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMovie))
            )
            .andExpect(status().isOk());

        // Validate the Movie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMovieUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMovie, movie), getPersistedMovie(movie));
    }

    @Test
    @Transactional
    void fullUpdateMovieWithPatch() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the movie using partial update
        Movie partialUpdatedMovie = new Movie();
        partialUpdatedMovie.setId(movie.getId());

        partialUpdatedMovie
            .name(UPDATED_NAME)
            .originName(UPDATED_ORIGIN_NAME)
            .isCompleted(UPDATED_IS_COMPLETED)
            .slug(UPDATED_SLUG)
            .episodeCurrent(UPDATED_EPISODE_CURRENT)
            .episodeTotal(UPDATED_EPISODE_TOTAL)
            .quality(UPDATED_QUALITY)
            .year(UPDATED_YEAR)
            .trailerUrl(UPDATED_TRAILER_URL)
            .time(UPDATED_TIME)
            .content(UPDATED_CONTENT)
            .isSingle(UPDATED_IS_SINGLE)
            .thumbUrl(UPDATED_THUMB_URL)
            .posterUrl(UPDATED_POSTER_URL)
            .actors(UPDATED_ACTORS)
            .country(UPDATED_COUNTRY)
            .premiumOnly(UPDATED_PREMIUM_ONLY);

        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMovie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMovie))
            )
            .andExpect(status().isOk());

        // Validate the Movie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMovieUpdatableFieldsEquals(partialUpdatedMovie, getPersistedMovie(partialUpdatedMovie));
    }

    @Test
    @Transactional
    void patchNonExistingMovie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movie.setId(longCount.incrementAndGet());

        // Create the Movie
        MovieDTO movieDTO = movieMapper.toDto(movie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, movieDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(movieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMovie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movie.setId(longCount.incrementAndGet());

        // Create the Movie
        MovieDTO movieDTO = movieMapper.toDto(movie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(movieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMovie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movie.setId(longCount.incrementAndGet());

        // Create the Movie
        MovieDTO movieDTO = movieMapper.toDto(movie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(movieDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMovie() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the movie
        restMovieMockMvc
            .perform(delete(ENTITY_API_URL_ID, movie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return movieRepository.count();
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

    protected Movie getPersistedMovie(Movie movie) {
        return movieRepository.findById(movie.getId()).orElseThrow();
    }

    protected void assertPersistedMovieToMatchAllProperties(Movie expectedMovie) {
        assertMovieAllPropertiesEquals(expectedMovie, getPersistedMovie(expectedMovie));
    }

    protected void assertPersistedMovieToMatchUpdatableProperties(Movie expectedMovie) {
        assertMovieAllUpdatablePropertiesEquals(expectedMovie, getPersistedMovie(expectedMovie));
    }
}
