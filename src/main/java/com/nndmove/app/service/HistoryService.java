package com.nndmove.app.service;

import com.nndmove.app.domain.History;
import com.nndmove.app.repository.HistoryRepository;
import com.nndmove.app.service.dto.HistoryDTO;
import com.nndmove.app.service.mapper.HistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.nndmove.app.domain.History}.
 */
@Service
@Transactional
public class HistoryService {

    private final Logger log = LoggerFactory.getLogger(HistoryService.class);

    private final HistoryRepository historyRepository;

    private final HistoryMapper historyMapper;

    public HistoryService(HistoryRepository historyRepository, HistoryMapper historyMapper) {
        this.historyRepository = historyRepository;
        this.historyMapper = historyMapper;
    }

    /**
     * Save a history.
     *
     * @param historyDTO the entity to save.
     * @return the persisted entity.
     */
    public HistoryDTO save(HistoryDTO historyDTO) {
        log.debug("Request to save History : {}", historyDTO);
        History history = historyMapper.toEntity(historyDTO);
        history = historyRepository.save(history);
        return historyMapper.toDto(history);
    }

    /**
     * Update a history.
     *
     * @param historyDTO the entity to save.
     * @return the persisted entity.
     */
    public HistoryDTO update(HistoryDTO historyDTO) {
        log.debug("Request to update History : {}", historyDTO);
        History history = historyMapper.toEntity(historyDTO);
        history = historyRepository.save(history);
        return historyMapper.toDto(history);
    }

    /**
     * Partially update a history.
     *
     * @param historyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HistoryDTO> partialUpdate(HistoryDTO historyDTO) {
        log.debug("Request to partially update History : {}", historyDTO);

        return historyRepository
            .findById(historyDTO.getId())
            .map(existingHistory -> {
                historyMapper.partialUpdate(existingHistory, historyDTO);

                return existingHistory;
            })
            .map(historyRepository::save)
            .map(historyMapper::toDto);
    }

    /**
     * Get all the histories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Histories");
        return historyRepository.findAll(pageable).map(historyMapper::toDto);
    }

    /**
     * Get one history by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HistoryDTO> findOne(Long id) {
        log.debug("Request to get History : {}", id);
        return historyRepository.findById(id).map(historyMapper::toDto);
    }

    /**
     * Delete the history by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete History : {}", id);
        historyRepository.deleteById(id);
    }
}
