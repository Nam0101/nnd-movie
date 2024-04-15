package com.nndmove.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;

/**
 * A MovieGenres.
 */
@Entity
@Table(name = "movie_genres")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MovieGenres implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "genres" }, allowSetters = true)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "movies" }, allowSetters = true)
    private Genres genres;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public MovieGenres id(Long id) {
        this.setId(id);
        return this;
    }

    public MovieGenres movie(Movie movie) {
        this.setMovie(movie);
        return this;
    }

    public MovieGenres genres(Genres genres) {
        this.setGenres(genres);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieGenres)) {
            return false;
        }
        return getId() != null && getId().equals(((MovieGenres) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieGenres{" +
                "id=" + getId() +
                "}";
    }

    public Long getId() {
        return this.id;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public Genres getGenres() {
        return this.genres;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnoreProperties(value = { "genres" }, allowSetters = true)
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @JsonIgnoreProperties(value = { "movies" }, allowSetters = true)
    public void setGenres(Genres genres) {
        this.genres = genres;
    }
}
