package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.Genres;
import com.nndmove.app.service.dto.GenresDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface GenresMapper extends EntityMapper<GenresDTO, Genres> {
    @Override
    Genres toEntity(GenresDTO dto);

    @Override
    GenresDTO toDto(Genres entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Genres entity, GenresDTO dto);

    static Set<Long> entitiesToIdsSet(Set<Genres> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(Genres::getId).collect(Collectors.toSet());
    }

    static Set<Genres> idsToEntitiesSet(Set<Long> ids) {
        if (ids == null) {
            return null;
        }
        return ids
            .stream()
            .map(id -> {
                Genres entity = new Genres();
                entity.setId(id);
                return entity;
            })
            .collect(Collectors.toSet());
    }
}
