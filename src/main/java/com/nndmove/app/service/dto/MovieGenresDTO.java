package com.nndmove.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO for the {@link com.nndmove.app.domain.MovieGenres} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@NoArgsConstructor
public class MovieGenresDTO implements Serializable {

    private Long id;

    private MovieDTO movie;

    private GenresDTO genres;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieGenresDTO)) {
            return false;
        }

        MovieGenresDTO movieGenresDTO = (MovieGenresDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, movieGenresDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieGenresDTO{" +
            "id=" + getId() +
            ", movie=" + getMovie() +
            ", genres=" + getGenres() +
            "}";
    }
}
