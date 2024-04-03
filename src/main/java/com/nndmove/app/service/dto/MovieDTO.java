package com.nndmove.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.nndmove.app.domain.Movie} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MovieDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String originName;

    @NotNull
    private Boolean isCompleted;

    @NotNull
    private String slug;

    @NotNull
    private String episodeCurrent;

    @NotNull
    private Integer episodeTotal;

    @NotNull
    private String quality;

    @NotNull
    private Integer year;

    private String trailerUrl;

    private String time;

    private String content;

    @NotNull
    private Boolean isSingle;

    private String thumbUrl;

    private String posterUrl;

    private String actors;

    private String country;

    @NotNull
    private Boolean premiumOnly;

    private Set<GenresDTO> genres = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getEpisodeCurrent() {
        return episodeCurrent;
    }

    public void setEpisodeCurrent(String episodeCurrent) {
        this.episodeCurrent = episodeCurrent;
    }

    public Integer getEpisodeTotal() {
        return episodeTotal;
    }

    public void setEpisodeTotal(Integer episodeTotal) {
        this.episodeTotal = episodeTotal;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsSingle() {
        return isSingle;
    }

    public void setIsSingle(Boolean isSingle) {
        this.isSingle = isSingle;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getPremiumOnly() {
        return premiumOnly;
    }

    public void setPremiumOnly(Boolean premiumOnly) {
        this.premiumOnly = premiumOnly;
    }

    public Set<GenresDTO> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenresDTO> genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieDTO)) {
            return false;
        }

        MovieDTO movieDTO = (MovieDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, movieDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieDTO{" +
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
            ", genres=" + getGenres() +
            "}";
    }
}
