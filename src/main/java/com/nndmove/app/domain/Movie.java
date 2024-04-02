package com.nndmove.app.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Movie.
 */
@Entity
@Table(name = "movie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "origin_name")
    private String originName;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @Column(name = "slug")
    private String slug;

    @Column(name = "episode_current")
    private String episodeCurrent;

    @Column(name = "episode_total")
    private Integer episodeTotal;

    @Column(name = "quality")
    private String quality;

    @Column(name = "year")
    private Integer year;

    @Column(name = "trailer_url")
    private String trailerUrl;

    @Column(name = "time")
    private String time;

    @Column(name = "content")
    private String content;

    @Column(name = "is_single")
    private Boolean isSingle;

    @Column(name = "thumb_url")
    private String thumbUrl;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "actor")
    private String actor;

    @Column(name = "country")
    private String country;

    @Column(name = "premium_only")
    private Boolean premiumOnly;

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

    public String getActor() {
        return this.actor;
    }

    public Movie actor(String actor) {
        this.setActor(actor);
        return this;
    }

    public void setActor(String actor) {
        this.actor = actor;
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
            ", actor='" + getActor() + "'" +
            ", country='" + getCountry() + "'" +
            ", premiumOnly='" + getPremiumOnly() + "'" +
            "}";
    }
}