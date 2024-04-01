package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.History;
import com.nndmove.app.domain.Movie;
import com.nndmove.app.domain.User;
import com.nndmove.app.service.dto.HistoryDTO;
import com.nndmove.app.service.dto.MovieDTO;
import com.nndmove.app.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link History} and its DTO {@link HistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface HistoryMapper extends EntityMapper<HistoryDTO, History> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "movie", source = "movie", qualifiedByName = "movieId")
    HistoryDTO toDto(History s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("movieId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MovieDTO toDtoMovieId(Movie movie);
}
