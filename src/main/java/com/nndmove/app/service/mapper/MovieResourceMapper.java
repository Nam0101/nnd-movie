package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.MovieResource;
import com.nndmove.app.service.dto.MovieResourceDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MovieResourceMapper extends EntityMapper<MovieResourceDTO, MovieResource> {
    @Override
    @Mapping(target = "movie", expression = "java(dto.getMovieId() == null ? null : new Movie().id(dto.getMovieId()))")
    MovieResource toEntity(MovieResourceDTO dto);

    @Override
    @Mapping(target = "movieId", source = "movie.id")
    MovieResourceDTO toDto(MovieResource entity);

    @Override
    @Mapping(target = "movie", expression = "java(dto.getMovieId() == null ? entity.getMovie() : new Movie().id(dto.getMovieId()))")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget MovieResource entity, MovieResourceDTO dto);

    static Set<Long> entitiesToIdsSet(Set<MovieResource> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(MovieResource::getId).collect(Collectors.toSet());
    }

    static Set<MovieResource> idsToEntitiesSet(Set<Long> ids) {
        if (ids == null) {
            return null;
        }
        return ids
            .stream()
            .map(id -> {
                MovieResource entity = new MovieResource();
                entity.setId(id);
                return entity;
            })
            .collect(Collectors.toSet());
    }
}
