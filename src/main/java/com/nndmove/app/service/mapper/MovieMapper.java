package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.Genres;
import com.nndmove.app.domain.Movie;
import com.nndmove.app.service.dto.GenresDTO;
import com.nndmove.app.service.dto.MovieDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Movie} and its DTO {@link MovieDTO}.
 */
@Mapper(componentModel = "spring")
public interface MovieMapper extends EntityMapper<MovieDTO, Movie> {
    @Mapping(target = "genres", source = "genres", qualifiedByName = "genresIdSet")
    MovieDTO toDto(Movie s);

    @Mapping(target = "removeGenres", ignore = true)
    Movie toEntity(MovieDTO movieDTO);

    @Named("genresId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GenresDTO toDtoGenresId(Genres genres);

    @Named("genresIdSet")
    default Set<GenresDTO> toDtoGenresIdSet(Set<Genres> genres) {
        return genres.stream().map(this::toDtoGenresId).collect(Collectors.toSet());
    }
}
