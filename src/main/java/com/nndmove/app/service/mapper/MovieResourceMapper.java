package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.Movie;
import com.nndmove.app.domain.MovieResource;
import com.nndmove.app.service.dto.MovieDTO;
import com.nndmove.app.service.dto.MovieResourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MovieResource} and its DTO {@link MovieResourceDTO}.
 */
@Mapper(componentModel = "spring")
public interface MovieResourceMapper extends EntityMapper<MovieResourceDTO, MovieResource> {
    @Mapping(target = "movie", source = "movie", qualifiedByName = "movieId")
    MovieResourceDTO toDto(MovieResource s);

    @Named("movieId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MovieDTO toDtoMovieId(Movie movie);
}
