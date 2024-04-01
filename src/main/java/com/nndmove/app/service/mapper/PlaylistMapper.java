package com.nndmove.app.service.mapper;

import com.nndmove.app.domain.Movie;
import com.nndmove.app.domain.Playlist;
import com.nndmove.app.domain.User;
import com.nndmove.app.service.dto.MovieDTO;
import com.nndmove.app.service.dto.PlaylistDTO;
import com.nndmove.app.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Playlist} and its DTO {@link PlaylistDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlaylistMapper extends EntityMapper<PlaylistDTO, Playlist> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "movie", source = "movie", qualifiedByName = "movieId")
    PlaylistDTO toDto(Playlist s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("movieId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MovieDTO toDtoMovieId(Movie movie);
}
