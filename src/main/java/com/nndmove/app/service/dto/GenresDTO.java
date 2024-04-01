package com.nndmove.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.nndmove.app.domain.Genres} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GenresDTO implements Serializable {

    private Long id;

    private String genres;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GenresDTO)) {
            return false;
        }

        GenresDTO genresDTO = (GenresDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, genresDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GenresDTO{" +
            "id=" + getId() +
            ", genres='" + getGenres() + "'" +
            "}";
    }
}
