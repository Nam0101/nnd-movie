package com.nndmove.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Genres.
 */
@Entity
@Table(name = "genres")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Genres implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "genres", nullable = false)
    private String genres;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "genres")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "genres" }, allowSetters = true)
    private Set<Movie> movies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Genres id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenres() {
        return this.genres;
    }

    public Genres genres(String genres) {
        this.setGenres(genres);
        return this;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public Set<Movie> getMovies() {
        return this.movies;
    }

    public void setMovies(Set<Movie> movies) {
        if (this.movies != null) {
            this.movies.forEach(i -> i.removeGenres(this));
        }
        if (movies != null) {
            movies.forEach(i -> i.addGenres(this));
        }
        this.movies = movies;
    }

    public Genres movies(Set<Movie> movies) {
        this.setMovies(movies);
        return this;
    }

    public Genres addMovie(Movie movie) {
        this.movies.add(movie);
        movie.getGenres().add(this);
        return this;
    }

    public Genres removeMovie(Movie movie) {
        this.movies.remove(movie);
        movie.getGenres().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Genres)) {
            return false;
        }
        return getId() != null && getId().equals(((Genres) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Genres{" +
            "id=" + getId() +
            ", genres='" + getGenres() + "'" +
            "}";
    }
}
