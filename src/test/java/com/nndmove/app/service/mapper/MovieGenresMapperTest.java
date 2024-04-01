package com.nndmove.app.service.mapper;

import static com.nndmove.app.domain.MovieGenresAsserts.*;
import static com.nndmove.app.domain.MovieGenresTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieGenresMapperTest {

    private MovieGenresMapper movieGenresMapper;

    @BeforeEach
    void setUp() {
        movieGenresMapper = new MovieGenresMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMovieGenresSample1();
        var actual = movieGenresMapper.toEntity(movieGenresMapper.toDto(expected));
        assertMovieGenresAllPropertiesEquals(expected, actual);
    }
}
