package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.Premium;
import com.nndmove.app.domain.User;
import com.nndmove.app.service.dto.PremiumDTO;
import com.nndmove.app.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Premium} and its DTO {@link PremiumDTO}.
 */
@Mapper(componentModel = "spring")
public interface PremiumMapper extends EntityMapper<PremiumDTO, Premium> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    PremiumDTO toDto(Premium s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
