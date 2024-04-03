package com.nndmove.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Movie.
 */
@Entity
@Table(name = "movie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Movie implements Serializable {

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

    public Long getId() {
        return this.id;
    }

    public Movie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Movie name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginName() {
        return this.originName;
    }

    public Movie originName(String originName) {
        this.setOriginName(originName);
        return this;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public Boolean getIsCompleted() {
        return this.isCompleted;
    }

    public Movie isCompleted(Boolean isCompleted) {
        this.setIsCompleted(isCompleted);
        return this;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getSlug() {
        return this.slug;
    }

    public Movie slug(String slug) {
        this.setSlug(slug);
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getEpisodeCurrent() {
        return this.episodeCurrent;
    }

    public Movie episodeCurrent(String episodeCurrent) {
        this.setEpisodeCurrent(episodeCurrent);
        return this;
    }

    public void setEpisodeCurrent(String episodeCurrent) {
        this.episodeCurrent = episodeCurrent;
    }

    public Integer getEpisodeTotal() {
        return this.episodeTotal;
    }

    public Movie episodeTotal(Integer episodeTotal) {
        this.setEpisodeTotal(episodeTotal);
        return this;
    }

    public void setEpisodeTotal(Integer episodeTotal) {
        this.episodeTotal = episodeTotal;
    }

    public String getQuality() {
        return this.quality;
    }

    public Movie quality(String quality) {
        this.setQuality(quality);
        return this;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Integer getYear() {
        return this.year;
    }

    public Movie year(Integer year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTrailerUrl() {
        return this.trailerUrl;
    }

    public Movie trailerUrl(String trailerUrl) {
        this.setTrailerUrl(trailerUrl);
        return this;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getTime() {
        return this.time;
    }

    public Movie time(String time) {
        this.setTime(time);
        return this;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return this.content;
    }

    public Movie content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsSingle() {
        return this.isSingle;
    }

    public Movie isSingle(Boolean isSingle) {
        this.setIsSingle(isSingle);
        return this;
    }

    public void setIsSingle(Boolean isSingle) {
        this.isSingle = isSingle;
    }

    public String getThumbUrl() {
        return this.thumbUrl;
    }

    public Movie thumbUrl(String thumbUrl) {
        this.setThumbUrl(thumbUrl);
        return this;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getPosterUrl() {
        return this.posterUrl;
    }

    public Movie posterUrl(String posterUrl) {
        this.setPosterUrl(posterUrl);
        return this;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getActors() {
        return this.actors;
    }

    public Movie actors(String actors) {
        this.setActors(actors);
        return this;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getCountry() {
        return this.country;
    }

    public Movie country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getPremiumOnly() {
        return this.premiumOnly;
    }

    public Movie premiumOnly(Boolean premiumOnly) {
        this.setPremiumOnly(premiumOnly);
        return this;
    }

    public void setPremiumOnly(Boolean premiumOnly) {
        this.premiumOnly = premiumOnly;
    }

    public Set<Genres> getGenres() {
        return this.genres;
    }

    public void setGenres(Set<Genres> genres) {
        this.genres = genres;
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
