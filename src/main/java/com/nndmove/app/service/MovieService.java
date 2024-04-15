package com.nndmove.app.service;

import com.nndmove.app.domain.Movie;
import com.nndmove.app.repository.MovieRepository;
import com.nndmove.app.service.dto.MovieDTO;
import com.nndmove.app.service.mapper.MovieMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.nndmove.app.domain.Movie}.
 */
@Service
@Transactional
public class MovieService {

    private final Logger log = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    /**
     * Save a movie.
     *
     * @param movieDTO the entity to save.
     * @return the persisted entity.
     */
    public MovieDTO save(MovieDTO movieDTO) {
        log.debug("Request to save Movie : {}", movieDTO);
        Movie movie = movieMapper.toEntity(movieDTO);
        movie = movieRepository.save(movie);
        return movieMapper.toDto(movie);
    }

    /**
     * Update a movie.
     *
     * @param movieDTO the entity to save.
     * @return the persisted entity.
     */
    public MovieDTO update(MovieDTO movieDTO) {
        log.debug("Request to update Movie : {}", movieDTO);
        Movie movie = movieMapper.toEntity(movieDTO);
        movie = movieRepository.save(movie);
        return movieMapper.toDto(movie);
    }

    /**
     * Partially update a movie.
     *
     * @param movieDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MovieDTO> partialUpdate(MovieDTO movieDTO) {
        log.debug("Request to partially update Movie : {}", movieDTO);

        return movieRepository
            .findById(movieDTO.id)
            .map(existingMovie -> {
                movieMapper.partialUpdate(existingMovie, movieDTO);

                return existingMovie;
            })
            .map(movieRepository::save)
            .map(movieMapper::toDto);
    }

    /**
     * Get all the movies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MovieDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Movies");
        return movieRepository.findAll(pageable).map(movieMapper::toDto);
    }

    /**
     * Get all the movies with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<MovieDTO> findAllWithEagerRelationships(Pageable pageable) {
        return movieRepository.findAllWithEagerRelationships(pageable).map(movieMapper::toDto);
    }

    /**
     * Get one movie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieDTO> findOne(Long id) {
        log.debug("Request to get Movie : {}", id);
        return movieRepository.findOneWithEagerRelationships(id).map(movieMapper::toDto);
    }

    /**
     * Delete the movie by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Movie : {}", id);
        movieRepository.deleteById(id);
    }
}
