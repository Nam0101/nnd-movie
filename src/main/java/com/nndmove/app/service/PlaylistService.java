package com.nndmove.app.service;

import com.nndmove.app.domain.Playlist;
import com.nndmove.app.repository.PlaylistRepository;
import com.nndmove.app.service.dto.PlaylistDTO;
import com.nndmove.app.service.mapper.PlaylistMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.nndmove.app.domain.Playlist}.
 */
@Service
@Transactional
public class PlaylistService {

    private final Logger log = LoggerFactory.getLogger(PlaylistService.class);

    private final PlaylistRepository playlistRepository;

    private final PlaylistMapper playlistMapper;

    public PlaylistService(PlaylistRepository playlistRepository, PlaylistMapper playlistMapper) {
        this.playlistRepository = playlistRepository;
        this.playlistMapper = playlistMapper;
    }

    /**
     * Save a playlist.
     *
     * @param playlistDTO the entity to save.
     * @return the persisted entity.
     */
    public PlaylistDTO save(PlaylistDTO playlistDTO) {
        log.debug("Request to save Playlist : {}", playlistDTO);
        Playlist playlist = playlistMapper.toEntity(playlistDTO);
        playlist = playlistRepository.save(playlist);
        return playlistMapper.toDto(playlist);
    }

    /**
     * Update a playlist.
     *
     * @param playlistDTO the entity to save.
     * @return the persisted entity.
     */
    public PlaylistDTO update(PlaylistDTO playlistDTO) {
        log.debug("Request to update Playlist : {}", playlistDTO);
        Playlist playlist = playlistMapper.toEntity(playlistDTO);
        playlist = playlistRepository.save(playlist);
        return playlistMapper.toDto(playlist);
    }

    /**
     * Partially update a playlist.
     *
     * @param playlistDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlaylistDTO> partialUpdate(PlaylistDTO playlistDTO) {
        log.debug("Request to partially update Playlist : {}", playlistDTO);

        return playlistRepository
            .findById(playlistDTO.id)
            .map(existingPlaylist -> {
                playlistMapper.partialUpdate(existingPlaylist, playlistDTO);

                return existingPlaylist;
            })
            .map(playlistRepository::save)
            .map(playlistMapper::toDto);
    }

    /**
     * Get all the playlists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PlaylistDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Playlists");
        return playlistRepository.findAll(pageable).map(playlistMapper::toDto);
    }

    /**
     * Get one playlist by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlaylistDTO> findOne(Long id) {
        log.debug("Request to get Playlist : {}", id);
        return playlistRepository.findById(id).map(playlistMapper::toDto);
    }

    /**
     * Delete the playlist by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Playlist : {}", id);
        playlistRepository.deleteById(id);
    }
}
