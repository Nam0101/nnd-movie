package com.nndmove.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.nndmove.app.domain.Genres} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GenresDTO implements Serializable {

    private Long id;

    @NotNull
    private String genres;

    private Set<MovieDTO> movies = new HashSet<>();

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

    public Set<MovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieDTO> movies) {
        this.movies = movies;
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
            ", movies=" + getMovies() +
            "}";
    }
}
