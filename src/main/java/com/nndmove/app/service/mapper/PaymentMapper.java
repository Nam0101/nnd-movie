package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.Payment;
import com.nndmove.app.service.dto.PaymentDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {
    @Override
    @Mapping(target = "user", expression = "java(dto.getUserId() == null ? null : new User().id(dto.getUserId()))")
    Payment toEntity(PaymentDTO dto);

    @Override
    @Mapping(target = "userId", source = "user.id")
    PaymentDTO toDto(Payment entity);

    @Override
    @Mapping(target = "user", expression = "java(dto.getUserId() == null ? entity.getUser() : new User().id(dto.getUserId()))")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Payment entity, PaymentDTO dto);

    static Set<Long> entitiesToIdsSet(Set<Payment> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(Payment::getId).collect(Collectors.toSet());
    }

    static Set<Payment> idsToEntitiesSet(Set<Long> ids) {
        if (ids == null) {
            return null;
        }
        return ids
            .stream()
            .map(id -> {
                Payment entity = new Payment();
                entity.setId(id);
                return entity;
            })
            .collect(Collectors.toSet());
    }
}
