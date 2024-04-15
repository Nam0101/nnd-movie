package com.nndmove.app.web.rest;

import com.nndmove.app.repository.HistoryRepository;
import com.nndmove.app.service.HistoryService;
import com.nndmove.app.service.dto.HistoryDTO;
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
 * REST controller for managing {@link com.nndmove.app.domain.History}.
 */
@RestController
@RequestMapping("/api/histories")
public class HistoryResource {

    private final Logger log = LoggerFactory.getLogger(HistoryResource.class);

    private static final String ENTITY_NAME = "history";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistoryService historyService;

    private final HistoryRepository historyRepository;

    public HistoryResource(HistoryService historyService, HistoryRepository historyRepository) {
        this.historyService = historyService;
        this.historyRepository = historyRepository;
    }

    /**
     * {@code POST  /histories} : Create a new history.
     *
     * @param historyDTO the historyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historyDTO, or with status {@code 400 (Bad Request)} if the history has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HistoryDTO> createHistory(@Valid @RequestBody HistoryDTO historyDTO) throws URISyntaxException {
        log.debug("REST request to save History : {}", historyDTO);
        if (historyDTO.id != null) {
            throw new BadRequestAlertException("A new history cannot already have an ID", ENTITY_NAME, "idexists");
        }
        historyDTO = historyService.save(historyDTO);
        return ResponseEntity.created(new URI("/api/histories/" + historyDTO.id))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, historyDTO.id.toString()))
            .body(historyDTO);
    }

    /**
     * {@code PUT  /histories/:id} : Updates an existing history.
     *
     * @param id the id of the historyDTO to save.
     * @param historyDTO the historyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historyDTO,
     * or with status {@code 400 (Bad Request)} if the historyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HistoryDTO> updateHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HistoryDTO historyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update History : {}, {}", id, historyDTO);
        if (historyDTO.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historyDTO.id)) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        historyDTO = historyService.update(historyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, historyDTO.id.toString()))
            .body(historyDTO);
    }

    /**
     * {@code PATCH  /histories/:id} : Partial updates given fields of an existing history, field will ignore if it is null
     *
     * @param id the id of the historyDTO to save.
     * @param historyDTO the historyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historyDTO,
     * or with status {@code 400 (Bad Request)} if the historyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the historyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the historyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HistoryDTO> partialUpdateHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HistoryDTO historyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update History partially : {}, {}", id, historyDTO);
        if (historyDTO.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historyDTO.id)) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HistoryDTO> result = historyService.partialUpdate(historyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, historyDTO.id.toString())
        );
    }

    /**
     * {@code GET  /histories} : get all the histories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of histories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<HistoryDTO>> getAllHistories(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Histories");
        Page<HistoryDTO> page = historyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /histories/:id} : get the "id" history.
     *
     * @param id the id of the historyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HistoryDTO> getHistory(@PathVariable("id") Long id) {
        log.debug("REST request to get History : {}", id);
        Optional<HistoryDTO> historyDTO = historyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(historyDTO);
    }

    /**
     * {@code DELETE  /histories/:id} : delete the "id" history.
     *
     * @param id the id of the historyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable("id") Long id) {
        log.debug("REST request to delete History : {}", id);
        historyService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
