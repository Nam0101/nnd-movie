package com.nndmove.app.service.mapper;

import static com.nndmove.app.domain.PlaylistAsserts.*;
import static com.nndmove.app.domain.PlaylistTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlaylistMapperTest {

    private PlaylistMapper playlistMapper;

    @BeforeEach
    void setUp() {
        playlistMapper = new PlaylistMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPlaylistSample1();
        var actual = playlistMapper.toEntity(playlistMapper.toDto(expected));
        assertPlaylistAllPropertiesEquals(expected, actual);
    }
}
