package com.nndmove.app.service.mapper;

import static com.nndmove.app.domain.GenresAsserts.*;
import static com.nndmove.app.domain.GenresTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenresMapperTest {

    private GenresMapper genresMapper;

    @BeforeEach
    void setUp() {
        genresMapper = new GenresMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getGenresSample1();
        var actual = genresMapper.toEntity(genresMapper.toDto(expected));
        assertGenresAllPropertiesEquals(expected, actual);
    }
}
