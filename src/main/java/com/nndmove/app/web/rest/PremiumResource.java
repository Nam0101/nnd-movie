package com.nndmove.app.web.rest;

import com.nndmove.app.repository.PremiumRepository;
import com.nndmove.app.service.PremiumService;
import com.nndmove.app.service.dto.PremiumDTO;
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
 * REST controller for managing {@link com.nndmove.app.domain.Premium}.
 */
@RestController
@RequestMapping("/api/premiums")
public class PremiumResource {

    private final Logger log = LoggerFactory.getLogger(PremiumResource.class);

    private static final String ENTITY_NAME = "premium";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PremiumService premiumService;

    private final PremiumRepository premiumRepository;

    public PremiumResource(PremiumService premiumService, PremiumRepository premiumRepository) {
        this.premiumService = premiumService;
        this.premiumRepository = premiumRepository;
    }

    /**
     * {@code POST  /premiums} : Create a new premium.
     *
     * @param premiumDTO the premiumDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new premiumDTO, or with status {@code 400 (Bad Request)} if the premium has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PremiumDTO> createPremium(@Valid @RequestBody PremiumDTO premiumDTO) throws URISyntaxException {
        log.debug("REST request to save Premium : {}", premiumDTO);
        if (premiumDTO.getId() != null) {
            throw new BadRequestAlertException("A new premium cannot already have an ID", ENTITY_NAME, "idexists");
        }
        premiumDTO = premiumService.save(premiumDTO);
        return ResponseEntity.created(new URI("/api/premiums/" + premiumDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, premiumDTO.getId().toString()))
            .body(premiumDTO);
    }

    /**
     * {@code PUT  /premiums/:id} : Updates an existing premium.
     *
     * @param id the id of the premiumDTO to save.
     * @param premiumDTO the premiumDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated premiumDTO,
     * or with status {@code 400 (Bad Request)} if the premiumDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the premiumDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PremiumDTO> updatePremium(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PremiumDTO premiumDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Premium : {}, {}", id, premiumDTO);
        if (premiumDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, premiumDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!premiumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        premiumDTO = premiumService.update(premiumDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, premiumDTO.getId().toString()))
            .body(premiumDTO);
    }

    /**
     * {@code PATCH  /premiums/:id} : Partial updates given fields of an existing premium, field will ignore if it is null
     *
     * @param id the id of the premiumDTO to save.
     * @param premiumDTO the premiumDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated premiumDTO,
     * or with status {@code 400 (Bad Request)} if the premiumDTO is not valid,
     * or with status {@code 404 (Not Found)} if the premiumDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the premiumDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PremiumDTO> partialUpdatePremium(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PremiumDTO premiumDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Premium partially : {}, {}", id, premiumDTO);
        if (premiumDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, premiumDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!premiumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PremiumDTO> result = premiumService.partialUpdate(premiumDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, premiumDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /premiums} : get all the premiums.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of premiums in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PremiumDTO>> getAllPremiums(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Premiums");
        Page<PremiumDTO> page = premiumService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /premiums/:id} : get the "id" premium.
     *
     * @param id the id of the premiumDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the premiumDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PremiumDTO> getPremium(@PathVariable("id") Long id) {
        log.debug("REST request to get Premium : {}", id);
        Optional<PremiumDTO> premiumDTO = premiumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(premiumDTO);
    }

    /**
     * {@code DELETE  /premiums/:id} : delete the "id" premium.
     *
     * @param id the id of the premiumDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePremium(@PathVariable("id") Long id) {
        log.debug("REST request to delete Premium : {}", id);
        premiumService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
