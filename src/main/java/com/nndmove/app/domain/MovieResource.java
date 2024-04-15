package com.nndmove.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * A MovieResource.
 */
@Entity
@Table(name = "movie_resource")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MovieResource implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "part", nullable = false)
    private Integer part;

    @NotNull
    @Column(name = "link_embed", nullable = false)
    private String linkEmbed;

    @NotNull
    @Column(name = "link_m_3_u_8", nullable = false)
    private String linkM3u8;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "genres" }, allowSetters = true)
    private Movie movie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public MovieResource id(Long id) {
        this.setId(id);
        return this;
    }

    public MovieResource part(Integer part) {
        this.setPart(part);
        return this;
    }

    public MovieResource linkEmbed(String linkEmbed) {
        this.setLinkEmbed(linkEmbed);
        return this;
    }

    public MovieResource linkM3u8(String linkM3u8) {
        this.setLinkM3u8(linkM3u8);
        return this;
    }

    public MovieResource movie(Movie movie) {
        this.setMovie(movie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieResource)) {
            return false;
        }
        return getId() != null && getId().equals(((MovieResource) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieResource{" +
                "id=" + getId() +
                ", part=" + getPart() +
                ", linkEmbed='" + getLinkEmbed() + "'" +
                ", linkM3u8='" + getLinkM3u8() + "'" +
                "}";
    }

    public Long getId() {
        return this.id;
    }

    public @NotNull Integer getPart() {
        return this.part;
    }

    public @NotNull String getLinkEmbed() {
        return this.linkEmbed;
    }

    public @NotNull String getLinkM3u8() {
        return this.linkM3u8;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPart(@NotNull Integer part) {
        this.part = part;
    }

    public void setLinkEmbed(@NotNull String linkEmbed) {
        this.linkEmbed = linkEmbed;
    }

    public void setLinkM3u8(@NotNull String linkM3u8) {
        this.linkM3u8 = linkM3u8;
    }

    @JsonIgnoreProperties(value = { "genres" }, allowSetters = true)
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
