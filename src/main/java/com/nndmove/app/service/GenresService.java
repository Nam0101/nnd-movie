package com.nndmove.app.service;

import com.nndmove.app.domain.Genres;
import com.nndmove.app.repository.GenresRepository;
import com.nndmove.app.service.dto.GenresDTO;
import com.nndmove.app.service.mapper.GenresMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.nndmove.app.domain.Genres}.
 */
@Service
@Transactional
public class GenresService {

    private final Logger log = LoggerFactory.getLogger(GenresService.class);

    private final GenresRepository genresRepository;

    private final GenresMapper genresMapper;

    public GenresService(GenresRepository genresRepository, GenresMapper genresMapper) {
        this.genresRepository = genresRepository;
        this.genresMapper = genresMapper;
    }

    /**
     * Save a genres.
     *
     * @param genresDTO the entity to save.
     * @return the persisted entity.
     */
    public GenresDTO save(GenresDTO genresDTO) {
        log.debug("Request to save Genres : {}", genresDTO);
        Genres genres = genresMapper.toEntity(genresDTO);
        genres = genresRepository.save(genres);
        return genresMapper.toDto(genres);
    }

    /**
     * Update a genres.
     *
     * @param genresDTO the entity to save.
     * @return the persisted entity.
     */
    public GenresDTO update(GenresDTO genresDTO) {
        log.debug("Request to update Genres : {}", genresDTO);
        Genres genres = genresMapper.toEntity(genresDTO);
        genres = genresRepository.save(genres);
        return genresMapper.toDto(genres);
    }

    /**
     * Partially update a genres.
     *
     * @param genresDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GenresDTO> partialUpdate(GenresDTO genresDTO) {
        log.debug("Request to partially update Genres : {}", genresDTO);

        return genresRepository
            .findById(genresDTO.id)
            .map(existingGenres -> {
                genresMapper.partialUpdate(existingGenres, genresDTO);

                return existingGenres;
            })
            .map(genresRepository::save)
            .map(genresMapper::toDto);
    }

    /**
     * Get all the genres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GenresDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Genres");
        return genresRepository.findAll(pageable).map(genresMapper::toDto);
    }

    /**
     * Get one genres by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GenresDTO> findOne(Long id) {
        log.debug("Request to get Genres : {}", id);
        return genresRepository.findById(id).map(genresMapper::toDto);
    }

    /**
     * Delete the genres by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Genres : {}", id);
        genresRepository.deleteById(id);
    }
}
