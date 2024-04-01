package com.nndmove.app.service;

import com.nndmove.app.domain.MovieResource;
import com.nndmove.app.repository.MovieResourceRepository;
import com.nndmove.app.service.dto.MovieResourceDTO;
import com.nndmove.app.service.mapper.MovieResourceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.nndmove.app.domain.MovieResource}.
 */
@Service
@Transactional
public class MovieResourceService {

    private final Logger log = LoggerFactory.getLogger(MovieResourceService.class);

    private final MovieResourceRepository movieResourceRepository;

    private final MovieResourceMapper movieResourceMapper;

    public MovieResourceService(MovieResourceRepository movieResourceRepository, MovieResourceMapper movieResourceMapper) {
        this.movieResourceRepository = movieResourceRepository;
        this.movieResourceMapper = movieResourceMapper;
    }

    /**
     * Save a movieResource.
     *
     * @param movieResourceDTO the entity to save.
     * @return the persisted entity.
     */
    public MovieResourceDTO save(MovieResourceDTO movieResourceDTO) {
        log.debug("Request to save MovieResource : {}", movieResourceDTO);
        MovieResource movieResource = movieResourceMapper.toEntity(movieResourceDTO);
        movieResource = movieResourceRepository.save(movieResource);
        return movieResourceMapper.toDto(movieResource);
    }

    /**
     * Update a movieResource.
     *
     * @param movieResourceDTO the entity to save.
     * @return the persisted entity.
     */
    public MovieResourceDTO update(MovieResourceDTO movieResourceDTO) {
        log.debug("Request to update MovieResource : {}", movieResourceDTO);
        MovieResource movieResource = movieResourceMapper.toEntity(movieResourceDTO);
        movieResource = movieResourceRepository.save(movieResource);
        return movieResourceMapper.toDto(movieResource);
    }

    /**
     * Partially update a movieResource.
     *
     * @param movieResourceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MovieResourceDTO> partialUpdate(MovieResourceDTO movieResourceDTO) {
        log.debug("Request to partially update MovieResource : {}", movieResourceDTO);

        return movieResourceRepository
            .findById(movieResourceDTO.getId())
            .map(existingMovieResource -> {
                movieResourceMapper.partialUpdate(existingMovieResource, movieResourceDTO);

                return existingMovieResource;
            })
            .map(movieResourceRepository::save)
            .map(movieResourceMapper::toDto);
    }

    /**
     * Get all the movieResources.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MovieResourceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MovieResources");
        return movieResourceRepository.findAll(pageable).map(movieResourceMapper::toDto);
    }

    /**
     * Get one movieResource by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieResourceDTO> findOne(Long id) {
        log.debug("Request to get MovieResource : {}", id);
        return movieResourceRepository.findById(id).map(movieResourceMapper::toDto);
    }

    /**
     * Delete the movieResource by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MovieResource : {}", id);
        movieResourceRepository.deleteById(id);
    }
}
