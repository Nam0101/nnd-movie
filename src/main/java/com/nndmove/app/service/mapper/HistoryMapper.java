package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.History;
import com.nndmove.app.service.dto.HistoryDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface HistoryMapper extends EntityMapper<HistoryDTO, History> {
    @Override
    @Mapping(target = "movie", expression = "java(dto.getMovieId() == null ? null : new Movie().id(dto.getMovieId()))")
    @Mapping(target = "user", expression = "java(dto.getUserId() == null ? null : new User().id(dto.getUserId()))")
    History toEntity(HistoryDTO dto);

    @Override
    @Mapping(target = "movieId", source = "movie.id")
    @Mapping(target = "userId", source = "user.id")
    HistoryDTO toDto(History entity);

    @Override
    @Mapping(target = "movie", expression = "java(dto.getMovieId() == null ? entity.getMovie() : new Movie().id(dto.getMovieId()))")
    @Mapping(target = "user", expression = "java(dto.getUserId() == null ? entity.getUser() : new User().id(dto.getUserId()))")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget History entity, HistoryDTO dto);

    static Set<Long> entitiesToIdsSet(Set<History> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(History::getId).collect(Collectors.toSet());
    }

    static Set<History> idsToEntitiesSet(Set<Long> ids) {
        if (ids == null) {
            return null;
        }
        return ids
            .stream()
            .map(id -> {
                History entity = new History();
                entity.setId(id);
                return entity;
            })
            .collect(Collectors.toSet());
    }
}
