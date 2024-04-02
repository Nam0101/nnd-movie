package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.User;
import com.nndmove.app.service.dto.UserDTO;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {
    @Override
    @Mapping(target = "resetDate", expression = "java(dto.getResetDateId() == null ? null : new Instant().id(dto.getResetDateId()))")
    @Mapping(target = "authorities", expression = "java(AuthorityMapper.idsToEntitiesSet(dto.getAuthoritiesIdsIds()))")
    User toEntity(UserDTO dto);

    @Override
    @Mapping(target = "resetDateId", source = "resetDate")
    @Mapping(target = "authoritiesIds", expression = "java(AuthorityMapper.entitiesToIdsSet(entity.getAuthoritiesIds()))")
    UserDTO toDto(User entity);

    @Override
    @Mapping(
        target = "resetDate",
        expression = "java(dto.getResetDateId() == null ? entity.getResetDate() : new Instant().id(dto.getResetDateId()))"
    )
    @Mapping(
        target = "authorities",
        expression = "java(java.util.Optional.ofNullable(AuthorityMapper.idsToEntitiesSet(dto.getAuthoritiesIdsIds())).orElse(entity.getAuthoritiesIds()))"
    )
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget User entity, UserDTO dto);

    static Set<Long> entitiesToIdsSet(Set<User> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(User::getId).collect(Collectors.toSet());
    }

    default Long map(Instant value) {
        return value == null ? null : value.toEpochMilli();
    }

    default Instant map(Long value) {
        return value == null ? null : Instant.ofEpochMilli(value);
    }

    static Set<User> idsToEntitiesSet(Set<Long> ids) {
        if (ids == null) {
            return null;
        }
        return ids
            .stream()
            .map(id -> {
                User entity = new User();
                entity.setId(id);
                return entity;
            })
            .collect(Collectors.toSet());
    }
}
