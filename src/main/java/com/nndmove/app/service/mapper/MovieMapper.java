package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.Movie;
import com.nndmove.app.service.dto.MovieDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MovieMapper extends EntityMapper<MovieDTO, Movie> {
    @Override
    Movie toEntity(MovieDTO dto);

    @Override
    MovieDTO toDto(Movie entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Movie entity, MovieDTO dto);

    static Set<Long> entitiesToIdsSet(Set<Movie> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(Movie::getId).collect(Collectors.toSet());
    }

    static Set<Movie> idsToEntitiesSet(Set<Long> ids) {
        if (ids == null) {
            return null;
        }
        return ids
            .stream()
            .map(id -> {
                Movie entity = new Movie();
                entity.setId(id);
                return entity;
            })
            .collect(Collectors.toSet());
    }
}
