package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.MovieGenres;
import com.nndmove.app.service.dto.MovieGenresDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MovieGenresMapper extends EntityMapper<MovieGenresDTO, MovieGenres> {
    @Override
    @Mapping(target = "genres", expression = "java(dto.getGenresId() == null ? null : new Genres().id(dto.getGenresId()))")
    @Mapping(target = "movie", expression = "java(dto.getMovieId() == null ? null : new Movie().id(dto.getMovieId()))")
    MovieGenres toEntity(MovieGenresDTO dto);

    @Override
    @Mapping(target = "genresId", source = "genres.id")
    @Mapping(target = "movieId", source = "movie.id")
    MovieGenresDTO toDto(MovieGenres entity);

    @Override
    @Mapping(target = "genres", expression = "java(dto.getGenresId() == null ? entity.getGenres() : new Genres().id(dto.getGenresId()))")
    @Mapping(target = "movie", expression = "java(dto.getMovieId() == null ? entity.getMovie() : new Movie().id(dto.getMovieId()))")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget MovieGenres entity, MovieGenresDTO dto);

    static Set<Long> entitiesToIdsSet(Set<MovieGenres> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(MovieGenres::getId).collect(Collectors.toSet());
    }

    static Set<MovieGenres> idsToEntitiesSet(Set<Long> ids) {
        if (ids == null) {
            return null;
        }
        return ids
            .stream()
            .map(id -> {
                MovieGenres entity = new MovieGenres();
                entity.setId(id);
                return entity;
            })
            .collect(Collectors.toSet());
    }
}
