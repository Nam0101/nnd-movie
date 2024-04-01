package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.Movie;
import com.nndmove.app.service.dto.MovieDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Movie} and its DTO {@link MovieDTO}.
 */
@Mapper(componentModel = "spring")
public interface MovieMapper extends EntityMapper<MovieDTO, Movie> {}
