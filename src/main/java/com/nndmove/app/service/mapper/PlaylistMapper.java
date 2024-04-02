package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.Playlist;
import com.nndmove.app.service.dto.PlaylistDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PlaylistMapper extends EntityMapper<PlaylistDTO, Playlist> {
    @Override
    @Mapping(target = "movie", expression = "java(dto.getMovieId() == null ? null : new Movie().id(dto.getMovieId()))")
    @Mapping(target = "user", expression = "java(dto.getUserId() == null ? null : new User().id(dto.getUserId()))")
    Playlist toEntity(PlaylistDTO dto);

    @Override
    @Mapping(target = "movieId", source = "movie.id")
    @Mapping(target = "userId", source = "user.id")
    PlaylistDTO toDto(Playlist entity);

    @Override
    @Mapping(target = "movie", expression = "java(dto.getMovieId() == null ? entity.getMovie() : new Movie().id(dto.getMovieId()))")
    @Mapping(target = "user", expression = "java(dto.getUserId() == null ? entity.getUser() : new User().id(dto.getUserId()))")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Playlist entity, PlaylistDTO dto);

    static Set<Long> entitiesToIdsSet(Set<Playlist> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(Playlist::getId).collect(Collectors.toSet());
    }

    static Set<Playlist> idsToEntitiesSet(Set<Long> ids) {
        if (ids == null) {
            return null;
        }
        return ids
            .stream()
            .map(id -> {
                Playlist entity = new Playlist();
                entity.setId(id);
                return entity;
            })
            .collect(Collectors.toSet());
    }
}
