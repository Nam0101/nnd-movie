package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.Payment;
import com.nndmove.app.domain.User;
import com.nndmove.app.service.dto.PaymentDTO;
import com.nndmove.app.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payment} and its DTO {@link PaymentDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    PaymentDTO toDto(Payment s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
