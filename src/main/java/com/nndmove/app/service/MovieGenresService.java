package com.nndmove.app.service;

import com.nndmove.app.domain.MovieGenres;
import com.nndmove.app.repository.MovieGenresRepository;
import com.nndmove.app.service.dto.MovieGenresDTO;
import com.nndmove.app.service.mapper.MovieGenresMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.nndmove.app.domain.MovieGenres}.
 */
@Service
@Transactional
public class MovieGenresService {

    private final Logger log = LoggerFactory.getLogger(MovieGenresService.class);

    private final MovieGenresRepository movieGenresRepository;

    private final MovieGenresMapper movieGenresMapper;

    public MovieGenresService(MovieGenresRepository movieGenresRepository, MovieGenresMapper movieGenresMapper) {
        this.movieGenresRepository = movieGenresRepository;
        this.movieGenresMapper = movieGenresMapper;
    }

    /**
     * Save a movieGenres.
     *
     * @param movieGenresDTO the entity to save.
     * @return the persisted entity.
     */
    public MovieGenresDTO save(MovieGenresDTO movieGenresDTO) {
        log.debug("Request to save MovieGenres : {}", movieGenresDTO);
        MovieGenres movieGenres = movieGenresMapper.toEntity(movieGenresDTO);
        movieGenres = movieGenresRepository.save(movieGenres);
        return movieGenresMapper.toDto(movieGenres);
    }

    /**
     * Update a movieGenres.
     *
     * @param movieGenresDTO the entity to save.
     * @return the persisted entity.
     */
    public MovieGenresDTO update(MovieGenresDTO movieGenresDTO) {
        log.debug("Request to update MovieGenres : {}", movieGenresDTO);
        MovieGenres movieGenres = movieGenresMapper.toEntity(movieGenresDTO);
        movieGenres = movieGenresRepository.save(movieGenres);
        return movieGenresMapper.toDto(movieGenres);
    }

    /**
     * Partially update a movieGenres.
     *
     * @param movieGenresDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MovieGenresDTO> partialUpdate(MovieGenresDTO movieGenresDTO) {
        log.debug("Request to partially update MovieGenres : {}", movieGenresDTO);

        return movieGenresRepository
            .findById(movieGenresDTO.getId())
            .map(existingMovieGenres -> {
                movieGenresMapper.partialUpdate(existingMovieGenres, movieGenresDTO);

                return existingMovieGenres;
            })
            .map(movieGenresRepository::save)
            .map(movieGenresMapper::toDto);
    }

    /**
     * Get all the movieGenres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MovieGenresDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MovieGenres");
        return movieGenresRepository.findAll(pageable).map(movieGenresMapper::toDto);
    }

    /**
     * Get one movieGenres by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieGenresDTO> findOne(Long id) {
        log.debug("Request to get MovieGenres : {}", id);
        return movieGenresRepository.findById(id).map(movieGenresMapper::toDto);
    }

    /**
     * Delete the movieGenres by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MovieGenres : {}", id);
        movieGenresRepository.deleteById(id);
    }
}
