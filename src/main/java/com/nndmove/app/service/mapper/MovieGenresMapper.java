package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.Genres;
import com.nndmove.app.domain.Movie;
import com.nndmove.app.domain.MovieGenres;
import com.nndmove.app.service.dto.GenresDTO;
import com.nndmove.app.service.dto.MovieDTO;
import com.nndmove.app.service.dto.MovieGenresDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MovieGenres} and its DTO {@link MovieGenresDTO}.
 */
@Mapper(componentModel = "spring")
public interface MovieGenresMapper extends EntityMapper<MovieGenresDTO, MovieGenres> {
    @Mapping(target = "movie", source = "movie", qualifiedByName = "movieId")
    @Mapping(target = "genres", source = "genres", qualifiedByName = "genresId")
    MovieGenresDTO toDto(MovieGenres s);

    @Named("movieId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MovieDTO toDtoMovieId(Movie movie);

    @Named("genresId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GenresDTO toDtoGenresId(Genres genres);
}
