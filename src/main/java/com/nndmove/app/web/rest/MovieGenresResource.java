package com.nndmove.app.web.rest;

import com.nndmove.app.repository.MovieGenresRepository;
import com.nndmove.app.service.MovieGenresService;
import com.nndmove.app.service.dto.MovieGenresDTO;
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
 * REST controller for managing {@link com.nndmove.app.domain.MovieGenres}.
 */
@RestController
@RequestMapping("/api/movie-genres")
public class MovieGenresResource {

    private final Logger log = LoggerFactory.getLogger(MovieGenresResource.class);

    private static final String ENTITY_NAME = "movieGenres";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovieGenresService movieGenresService;

    private final MovieGenresRepository movieGenresRepository;

    public MovieGenresResource(MovieGenresService movieGenresService, MovieGenresRepository movieGenresRepository) {
        this.movieGenresService = movieGenresService;
        this.movieGenresRepository = movieGenresRepository;
    }

    /**
     * {@code POST  /movie-genres} : Create a new movieGenres.
     *
     * @param movieGenresDTO the movieGenresDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movieGenresDTO, or with status {@code 400 (Bad Request)} if the movieGenres has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MovieGenresDTO> createMovieGenres(@RequestBody MovieGenresDTO movieGenresDTO) throws URISyntaxException {
        log.debug("REST request to save MovieGenres : {}", movieGenresDTO);
        if (movieGenresDTO.getId() != null) {
            throw new BadRequestAlertException("A new movieGenres cannot already have an ID", ENTITY_NAME, "idexists");
        }
        movieGenresDTO = movieGenresService.save(movieGenresDTO);
        return ResponseEntity.created(new URI("/api/movie-genres/" + movieGenresDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, movieGenresDTO.getId().toString()))
            .body(movieGenresDTO);
    }

    /**
     * {@code PUT  /movie-genres/:id} : Updates an existing movieGenres.
     *
     * @param id the id of the movieGenresDTO to save.
     * @param movieGenresDTO the movieGenresDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movieGenresDTO,
     * or with status {@code 400 (Bad Request)} if the movieGenresDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movieGenresDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MovieGenresDTO> updateMovieGenres(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MovieGenresDTO movieGenresDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MovieGenres : {}, {}", id, movieGenresDTO);
        if (movieGenresDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movieGenresDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!movieGenresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        movieGenresDTO = movieGenresService.update(movieGenresDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, movieGenresDTO.getId().toString()))
            .body(movieGenresDTO);
    }

    /**
     * {@code PATCH  /movie-genres/:id} : Partial updates given fields of an existing movieGenres, field will ignore if it is null
     *
     * @param id the id of the movieGenresDTO to save.
     * @param movieGenresDTO the movieGenresDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movieGenresDTO,
     * or with status {@code 400 (Bad Request)} if the movieGenresDTO is not valid,
     * or with status {@code 404 (Not Found)} if the movieGenresDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the movieGenresDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MovieGenresDTO> partialUpdateMovieGenres(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MovieGenresDTO movieGenresDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MovieGenres partially : {}, {}", id, movieGenresDTO);
        if (movieGenresDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movieGenresDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!movieGenresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MovieGenresDTO> result = movieGenresService.partialUpdate(movieGenresDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, movieGenresDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /movie-genres} : get all the movieGenres.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movieGenres in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MovieGenresDTO>> getAllMovieGenres(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of MovieGenres");
        Page<MovieGenresDTO> page = movieGenresService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /movie-genres/:id} : get the "id" movieGenres.
     *
     * @param id the id of the movieGenresDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieGenresDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MovieGenresDTO> getMovieGenres(@PathVariable("id") Long id) {
        log.debug("REST request to get MovieGenres : {}", id);
        Optional<MovieGenresDTO> movieGenresDTO = movieGenresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movieGenresDTO);
    }

    /**
     * {@code DELETE  /movie-genres/:id} : delete the "id" movieGenres.
     *
     * @param id the id of the movieGenresDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieGenres(@PathVariable("id") Long id) {
        log.debug("REST request to delete MovieGenres : {}", id);
        movieGenresService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
