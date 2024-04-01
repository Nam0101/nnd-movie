package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.Genres;
import com.nndmove.app.service.dto.GenresDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Genres} and its DTO {@link GenresDTO}.
 */
@Mapper(componentModel = "spring")
public interface GenresMapper extends EntityMapper<GenresDTO, Genres> {}
