package com.nndmove.app.service.mapper;

import static com.nndmove.app.domain.MovieResourceAsserts.*;
import static com.nndmove.app.domain.MovieResourceTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieResourceMapperTest {

    private MovieResourceMapper movieResourceMapper;

    @BeforeEach
    void setUp() {
        movieResourceMapper = new MovieResourceMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMovieResourceSample1();
        var actual = movieResourceMapper.toEntity(movieResourceMapper.toDto(expected));
        assertMovieResourceAllPropertiesEquals(expected, actual);
    }
}
