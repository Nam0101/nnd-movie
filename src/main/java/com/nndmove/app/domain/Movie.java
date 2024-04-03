package com.nndmove.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * A Movie.
 */
@Setter
@Getter
@Entity
@Table(name = "movie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Movie implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "origin_name", nullable = false)
    private String originName;

    @NotNull
    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted;

    @NotNull
    @Column(name = "slug", nullable = false)
    private String slug;

    @NotNull
    @Column(name = "episode_current", nullable = false)
    private String episodeCurrent;

    @NotNull
    @Column(name = "episode_total", nullable = false)
    private Integer episodeTotal;

    @NotNull
    @Column(name = "quality", nullable = false)
    private String quality;

    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "trailer_url")
    private String trailerUrl;

    @Column(name = "time")
    private String time;

    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "is_single", nullable = false)
    private Boolean isSingle;

    @Column(name = "thumb_url")
    private String thumbUrl;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "actors")
    private String actors;

    @Column(name = "country")
    private String country;

    @NotNull
    @Column(name = "premium_only", nullable = false)
    private Boolean premiumOnly;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_movie__genres",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genres_id")
    )
    @JsonIgnoreProperties(value = { "movies" }, allowSetters = true)
    private Set<Genres> genres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Movie id(Long id) {
        this.setId(id);
        return this;
    }

    public Movie name(String name) {
        this.setName(name);
        return this;
    }

    public Movie originName(String originName) {
        this.setOriginName(originName);
        return this;
    }

    public Movie isCompleted(Boolean isCompleted) {
        this.setIsCompleted(isCompleted);
        return this;
    }

    public Movie slug(String slug) {
        this.setSlug(slug);
        return this;
    }

    public Movie episodeCurrent(String episodeCurrent) {
        this.setEpisodeCurrent(episodeCurrent);
        return this;
    }

    public Movie episodeTotal(Integer episodeTotal) {
        this.setEpisodeTotal(episodeTotal);
        return this;
    }

    public Movie quality(String quality) {
        this.setQuality(quality);
        return this;
    }

    public Movie year(Integer year) {
        this.setYear(year);
        return this;
    }

    public Movie trailerUrl(String trailerUrl) {
        this.setTrailerUrl(trailerUrl);
        return this;
    }

    public Movie time(String time) {
        this.setTime(time);
        return this;
    }

    public Movie content(String content) {
        this.setContent(content);
        return this;
    }

    public Movie isSingle(Boolean isSingle) {
        this.setIsSingle(isSingle);
        return this;
    }

    public Movie thumbUrl(String thumbUrl) {
        this.setThumbUrl(thumbUrl);
        return this;
    }

    public Movie posterUrl(String posterUrl) {
        this.setPosterUrl(posterUrl);
        return this;
    }

    public Movie actors(String actors) {
        this.setActors(actors);
        return this;
    }

    public Movie country(String country) {
        this.setCountry(country);
        return this;
    }

    public Movie premiumOnly(Boolean premiumOnly) {
        this.setPremiumOnly(premiumOnly);
        return this;
    }

    public Movie genres(Set<Genres> genres) {
        this.setGenres(genres);
        return this;
    }

    public Movie addGenres(Genres genres) {
        this.genres.add(genres);
        return this;
    }

    public Movie removeGenres(Genres genres) {
        this.genres.remove(genres);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie)) {
            return false;
        }
        return getId() != null && getId().equals(((Movie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Movie{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", originName='" + getOriginName() + "'" +
            ", isCompleted='" + getIsCompleted() + "'" +
            ", slug='" + getSlug() + "'" +
            ", episodeCurrent='" + getEpisodeCurrent() + "'" +
            ", episodeTotal=" + getEpisodeTotal() +
            ", quality='" + getQuality() + "'" +
            ", year=" + getYear() +
            ", trailerUrl='" + getTrailerUrl() + "'" +
            ", time='" + getTime() + "'" +
            ", content='" + getContent() + "'" +
            ", isSingle='" + getIsSingle() + "'" +
            ", thumbUrl='" + getThumbUrl() + "'" +
            ", posterUrl='" + getPosterUrl() + "'" +
            ", actors='" + getActors() + "'" +
            ", country='" + getCountry() + "'" +
            ", premiumOnly='" + getPremiumOnly() + "'" +
            "}";
    }
}
