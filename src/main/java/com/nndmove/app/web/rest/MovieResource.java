package com.nndmove.app.web.rest;

import com.nndmove.app.repository.MovieRepository;
import com.nndmove.app.service.MovieService;
import com.nndmove.app.service.dto.MovieDTO;
import com.nndmove.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.nndmove.app.domain.Movie}.
 */
@RestController
@RequestMapping("/api/movies")
public class MovieResource {

    private final Logger log = LoggerFactory.getLogger(MovieResource.class);

    private static final String ENTITY_NAME = "movie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovieService movieService;

    private final MovieRepository movieRepository;

    public MovieResource(MovieService movieService, MovieRepository movieRepository) {
        this.movieService = movieService;
        this.movieRepository = movieRepository;
    }

    /**
     * {@code POST  /movies} : Create a new movie.
     *
     * @param movieDTO the movieDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movieDTO, or with status {@code 400 (Bad Request)} if the movie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO) throws URISyntaxException {
        log.debug("REST request to save Movie : {}", movieDTO);
        if (movieDTO.getId() != null) {
            throw new BadRequestAlertException("A new movie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        movieDTO = movieService.save(movieDTO);
        return ResponseEntity.created(new URI("/api/movies/" + movieDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, movieDTO.getId().toString()))
            .body(movieDTO);
    }

    /**
     * {@code PUT  /movies/:id} : Updates an existing movie.
     *
     * @param id the id of the movieDTO to save.
     * @param movieDTO the movieDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movieDTO,
     * or with status {@code 400 (Bad Request)} if the movieDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MovieDTO movieDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Movie : {}, {}", id, movieDTO);
        if (movieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movieDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!movieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        movieDTO = movieService.update(movieDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, movieDTO.getId().toString()))
            .body(movieDTO);
    }

    /**
     * {@code PATCH  /movies/:id} : Partial updates given fields of an existing movie, field will ignore if it is null
     *
     * @param id the id of the movieDTO to save.
     * @param movieDTO the movieDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movieDTO,
     * or with status {@code 400 (Bad Request)} if the movieDTO is not valid,
     * or with status {@code 404 (Not Found)} if the movieDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the movieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MovieDTO> partialUpdateMovie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MovieDTO movieDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Movie partially : {}, {}", id, movieDTO);
        if (movieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movieDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!movieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MovieDTO> result = movieService.partialUpdate(movieDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, movieDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /movies} : get all the movies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movies in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MovieDTO>> getAllMovies(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Movies");
        Page<MovieDTO> page = movieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /movies/:id} : get the "id" movie.
     *
     * @param id the id of the movieDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovie(@PathVariable("id") Long id) {
        log.debug("REST request to get Movie : {}", id);
        Optional<MovieDTO> movieDTO = movieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movieDTO);
    }

    /**
     * {@code DELETE  /movies/:id} : delete the "id" movie.
     *
     * @param id the id of the movieDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Movie : {}", id);
        movieService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
