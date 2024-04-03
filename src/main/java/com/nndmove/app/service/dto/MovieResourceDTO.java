package com.nndmove.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.nndmove.app.domain.MovieResource} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MovieResourceDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer part;

    @NotNull
    private String linkEmbed;

    @NotNull
    private String linkM3u8;

    private MovieDTO movie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPart() {
        return part;
    }

    public void setPart(Integer part) {
        this.part = part;
    }

    public String getLinkEmbed() {
        return linkEmbed;
    }

    public void setLinkEmbed(String linkEmbed) {
        this.linkEmbed = linkEmbed;
    }

    public String getLinkM3u8() {
        return linkM3u8;
    }

    public void setLinkM3u8(String linkM3u8) {
        this.linkM3u8 = linkM3u8;
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
