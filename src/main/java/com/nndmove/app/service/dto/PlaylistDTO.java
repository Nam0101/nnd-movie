package com.nndmove.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.nndmove.app.domain.Playlist} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlaylistDTO implements Serializable {

    private Long id;

    private UserDTO user;

    private MovieDTO movie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public MovieDTO getMovie() {
        return movie;
    }

    public void setMovie(MovieDTO movie) {
        this.movie = movie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlaylistDTO)) {
            return false;
        }

        PlaylistDTO playlistDTO = (PlaylistDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, playlistDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaylistDTO{" +
            "id=" + getId() +
            ", user=" + getUser() +
            ", movie=" + getMovie() +
            "}";
    }
}
