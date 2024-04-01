package com.nndmove.app.service;

import com.nndmove.app.domain.Premium;
import com.nndmove.app.repository.PremiumRepository;
import com.nndmove.app.service.dto.PremiumDTO;
import com.nndmove.app.service.mapper.PremiumMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.nndmove.app.domain.Premium}.
 */
@Service
@Transactional
public class PremiumService {

    private final Logger log = LoggerFactory.getLogger(PremiumService.class);

    private final PremiumRepository premiumRepository;

    private final PremiumMapper premiumMapper;

    public PremiumService(PremiumRepository premiumRepository, PremiumMapper premiumMapper) {
        this.premiumRepository = premiumRepository;
        this.premiumMapper = premiumMapper;
    }

    /**
     * Save a premium.
     *
     * @param premiumDTO the entity to save.
     * @return the persisted entity.
     */
    public PremiumDTO save(PremiumDTO premiumDTO) {
        log.debug("Request to save Premium : {}", premiumDTO);
        Premium premium = premiumMapper.toEntity(premiumDTO);
        premium = premiumRepository.save(premium);
        return premiumMapper.toDto(premium);
    }

    /**
     * Update a premium.
     *
     * @param premiumDTO the entity to save.
     * @return the persisted entity.
     */
    public PremiumDTO update(PremiumDTO premiumDTO) {
        log.debug("Request to update Premium : {}", premiumDTO);
        Premium premium = premiumMapper.toEntity(premiumDTO);
        premium = premiumRepository.save(premium);
        return premiumMapper.toDto(premium);
    }

    /**
     * Partially update a premium.
     *
     * @param premiumDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PremiumDTO> partialUpdate(PremiumDTO premiumDTO) {
        log.debug("Request to partially update Premium : {}", premiumDTO);

        return premiumRepository
            .findById(premiumDTO.getId())
            .map(existingPremium -> {
                premiumMapper.partialUpdate(existingPremium, premiumDTO);

                return existingPremium;
            })
            .map(premiumRepository::save)
            .map(premiumMapper::toDto);
    }

    /**
     * Get all the premiums.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PremiumDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Premiums");
        return premiumRepository.findAll(pageable).map(premiumMapper::toDto);
    }

    /**
     * Get one premium by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PremiumDTO> findOne(Long id) {
        log.debug("Request to get Premium : {}", id);
        return premiumRepository.findById(id).map(premiumMapper::toDto);
    }

    /**
     * Delete the premium by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Premium : {}", id);
        premiumRepository.deleteById(id);
    }
}
