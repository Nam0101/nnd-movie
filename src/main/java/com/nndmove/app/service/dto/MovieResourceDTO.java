package com.nndmove.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO for the {@link com.nndmove.app.domain.MovieResource} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@NoArgsConstructor
public class MovieResourceDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer part;

    @NotNull
    private String linkEmbed;

    @NotNull
    private String linkM3u8;

    private MovieDTO movie;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieResourceDTO)) {
            return false;
        }

        MovieResourceDTO movieResourceDTO = (MovieResourceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, movieResourceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieResourceDTO{" +
            "id=" + getId() +
            ", part=" + getPart() +
            ", linkEmbed='" + getLinkEmbed() + "'" +
            ", linkM3u8='" + getLinkM3u8() + "'" +
            ", movie=" + getMovie() +
            "}";
    }
}
