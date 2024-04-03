package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.Genres;
import com.nndmove.app.domain.Movie;
import com.nndmove.app.service.dto.GenresDTO;
import com.nndmove.app.service.dto.MovieDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Genres} and its DTO {@link GenresDTO}.
 */
@Mapper(componentModel = "spring")
public interface GenresMapper extends EntityMapper<GenresDTO, Genres> {
    @Mapping(target = "movies", source = "movies", qualifiedByName = "movieIdSet")
    GenresDTO toDto(Genres s);

    @Mapping(target = "movies", ignore = true)
    @Mapping(target = "movies", ignore = true)
    Genres toEntity(GenresDTO genresDTO);

    @Named("movieId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MovieDTO toDtoMovieId(Movie movie);

    @Named("movieIdSet")
    default Set<MovieDTO> toDtoMovieIdSet(Set<Movie> movie) {
        return movie.stream().map(this::toDtoMovieId).collect(Collectors.toSet());
    }
}
