package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.Premium;
import com.nndmove.app.domain.User;
import com.nndmove.app.service.dto.PremiumDTO;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PremiumMapper extends EntityMapper<PremiumDTO, Premium> {
    @Override
    @Mapping(target = "endDate", expression = "java(dto.getEndDateId() == null ? null : new Instant().id(dto.getEndDateId()))")
    @Mapping(target = "startDate", expression = "java(dto.getStartDateId() == null ? null : new Instant().id(dto.getStartDateId()))")
    @Mapping(target = "user", expression = "java(dto.getUserId() == null ? null : new User().id(dto.getUserId()))")
    Premium toEntity(PremiumDTO dto);

    @Override
    @Mapping(target = "endDateId", source = "endDate")
    @Mapping(target = "startDateId", source = "startDate")
    @Mapping(target = "userId", source = "user.id")
    PremiumDTO toDto(Premium entity);

    @Override
    @Mapping(
        target = "endDate",
        expression = "java(dto.getEndDateId() == null ? entity.getEndDate() : new Instant().id(dto.getEndDateId()))"
    )
    @Mapping(
        target = "startDate",
        expression = "java(dto.getStartDateId() == null ? entity.getStartDate() : new Instant().id(dto.getStartDateId()))"
    )
    @Mapping(target = "user", expression = "java(dto.getUserId() == null ? entity.getUser() : new User().id(dto.getUserId()))")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Premium entity, PremiumDTO dto);

    default Long map(Instant value) {
        return value == null ? null : value.toEpochMilli();
    }

    default Instant map(Long value) {
        return value == null ? null : Instant.ofEpochMilli(value);
    }

    static Set<Long> entitiesToIdsSet(Set<Premium> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(Premium::getId).collect(Collectors.toSet());
    }

    static Set<Premium> idsToEntitiesSet(Set<Long> ids) {
        if (ids == null) {
            return null;
        }
        return ids
            .stream()
            .map(id -> {
                Premium entity = new Premium();
                entity.setId(id);
                return entity;
            })
            .collect(Collectors.toSet());
    }
}
