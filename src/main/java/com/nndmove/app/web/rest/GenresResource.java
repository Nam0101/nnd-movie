package com.nndmove.app.web.rest;

import com.nndmove.app.repository.GenresRepository;
import com.nndmove.app.service.GenresService;
import com.nndmove.app.service.dto.GenresDTO;
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
 * REST controller for managing {@link com.nndmove.app.domain.Genres}.
 */
@RestController
@RequestMapping("/api/genres")
public class GenresResource {

    private final Logger log = LoggerFactory.getLogger(GenresResource.class);

    private static final String ENTITY_NAME = "genres";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GenresService genresService;

    private final GenresRepository genresRepository;

    public GenresResource(GenresService genresService, GenresRepository genresRepository) {
        this.genresService = genresService;
        this.genresRepository = genresRepository;
    }

    /**
     * {@code POST  /genres} : Create a new genres.
     *
     * @param genresDTO the genresDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new genresDTO, or with status {@code 400 (Bad Request)} if the genres has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GenresDTO> createGenres(@Valid @RequestBody GenresDTO genresDTO) throws URISyntaxException {
        log.debug("REST request to save Genres : {}", genresDTO);
        if (genresDTO.id != null) {
            throw new BadRequestAlertException("A new genres cannot already have an ID", ENTITY_NAME, "idexists");
        }
        genresDTO = genresService.save(genresDTO);
        return ResponseEntity.created(new URI("/api/genres/" + genresDTO.id))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, genresDTO.id.toString()))
            .body(genresDTO);
    }

    /**
     * {@code PUT  /genres/:id} : Updates an existing genres.
     *
     * @param id the id of the genresDTO to save.
     * @param genresDTO the genresDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated genresDTO,
     * or with status {@code 400 (Bad Request)} if the genresDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the genresDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GenresDTO> updateGenres(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GenresDTO genresDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Genres : {}, {}", id, genresDTO);
        if (genresDTO.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, genresDTO.id)) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!genresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        genresDTO = genresService.update(genresDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, genresDTO.id.toString()))
            .body(genresDTO);
    }

    /**
     * {@code PATCH  /genres/:id} : Partial updates given fields of an existing genres, field will ignore if it is null
     *
     * @param id the id of the genresDTO to save.
     * @param genresDTO the genresDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated genresDTO,
     * or with status {@code 400 (Bad Request)} if the genresDTO is not valid,
     * or with status {@code 404 (Not Found)} if the genresDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the genresDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GenresDTO> partialUpdateGenres(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GenresDTO genresDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Genres partially : {}, {}", id, genresDTO);
        if (genresDTO.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, genresDTO.id)) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!genresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GenresDTO> result = genresService.partialUpdate(genresDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, genresDTO.id.toString())
        );
    }

    /**
     * {@code GET  /genres} : get all the genres.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of genres in body.
     */
    @GetMapping("")
    public ResponseEntity<List<GenresDTO>> getAllGenres(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Genres");
        Page<GenresDTO> page = genresService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /genres/:id} : get the "id" genres.
     *
     * @param id the id of the genresDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the genresDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GenresDTO> getGenres(@PathVariable("id") Long id) {
        log.debug("REST request to get Genres : {}", id);
        Optional<GenresDTO> genresDTO = genresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(genresDTO);
    }

    /**
     * {@code DELETE  /genres/:id} : delete the "id" genres.
     *
     * @param id the id of the genresDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenres(@PathVariable("id") Long id) {
        log.debug("REST request to delete Genres : {}", id);
        genresService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
