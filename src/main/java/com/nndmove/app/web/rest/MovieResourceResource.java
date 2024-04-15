package com.nndmove.app.web.rest;

import com.nndmove.app.repository.MovieResourceRepository;
import com.nndmove.app.service.MovieResourceService;
import com.nndmove.app.service.dto.MovieResourceDTO;
import com.nndmove.app.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.nndmove.app.domain.MovieResource}.
 */
@RestController
@RequestMapping("/api/movie-resources")
public class MovieResourceResource {

    private final Logger log = LoggerFactory.getLogger(MovieResourceResource.class);

    private static final String ENTITY_NAME = "movieResource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovieResourceService movieResourceService;

    private final MovieResourceRepository movieResourceRepository;

    public MovieResourceResource(MovieResourceService movieResourceService, MovieResourceRepository movieResourceRepository) {
        this.movieResourceService = movieResourceService;
        this.movieResourceRepository = movieResourceRepository;
    }

    /**
     * {@code POST  /movie-resources} : Create a new movieResource.
     *
     * @param movieResourceDTO the movieResourceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movieResourceDTO, or with status {@code 400 (Bad Request)} if the movieResource has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MovieResourceDTO> createMovieResource(@Valid @RequestBody MovieResourceDTO movieResourceDTO)
        throws URISyntaxException {
        log.debug("REST request to save MovieResource : {}", movieResourceDTO);
        if (movieResourceDTO.id != null) {
            throw new BadRequestAlertException("A new movieResource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        movieResourceDTO = movieResourceService.save(movieResourceDTO);
        return ResponseEntity.created(new URI("/api/movie-resources/" + movieResourceDTO.id))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, movieResourceDTO.id.toString()))
            .body(movieResourceDTO);
    }

    /**
     * {@code PUT  /movie-resources/:id} : Updates an existing movieResource.
     *
     * @param id the id of the movieResourceDTO to save.
     * @param movieResourceDTO the movieResourceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movieResourceDTO,
     * or with status {@code 400 (Bad Request)} if the movieResourceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movieResourceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MovieResourceDTO> updateMovieResource(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MovieResourceDTO movieResourceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MovieResource : {}, {}", id, movieResourceDTO);
        if (movieResourceDTO.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movieResourceDTO.id)) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!movieResourceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        movieResourceDTO = movieResourceService.update(movieResourceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, movieResourceDTO.id.toString()))
            .body(movieResourceDTO);
    }

    /**
     * {@code PATCH  /movie-resources/:id} : Partial updates given fields of an existing movieResource, field will ignore if it is null
     *
     * @param id the id of the movieResourceDTO to save.
     * @param movieResourceDTO the movieResourceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movieResourceDTO,
     * or with status {@code 400 (Bad Request)} if the movieResourceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the movieResourceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the movieResourceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MovieResourceDTO> partialUpdateMovieResource(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MovieResourceDTO movieResourceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MovieResource partially : {}, {}", id, movieResourceDTO);
        if (movieResourceDTO.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movieResourceDTO.id)) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!movieResourceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MovieResourceDTO> result = movieResourceService.partialUpdate(movieResourceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, movieResourceDTO.id.toString())
        );
    }

    /**
     * {@code GET  /movie-resources} : get all the movieResources.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movieResources in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MovieResourceDTO>> getAllMovieResources(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of MovieResources");
        Page<MovieResourceDTO> page = movieResourceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /movie-resources/:id} : get the "id" movieResource.
     *
     * @param id the id of the movieResourceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieResourceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MovieResourceDTO> getMovieResource(@PathVariable("id") Long id) {
        log.debug("REST request to get MovieResource : {}", id);
        Optional<MovieResourceDTO> movieResourceDTO = movieResourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movieResourceDTO);
    }

    /**
     * {@code DELETE  /movie-resources/:id} : delete the "id" movieResource.
     *
     * @param id the id of the movieResourceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieResource(@PathVariable("id") Long id) {
        log.debug("REST request to delete MovieResource : {}", id);
        movieResourceService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
