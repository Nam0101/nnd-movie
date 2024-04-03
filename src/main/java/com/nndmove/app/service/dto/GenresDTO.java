package com.nndmove.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO for the {@link com.nndmove.app.domain.Genres} entity.
 */
@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GenresDTO implements Serializable {

    private Long id;

    @NotNull
    private String genres;

    private Set<MovieDTO> movies = new HashSet<>();

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
