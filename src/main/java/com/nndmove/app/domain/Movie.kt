package com.nndmove.app.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.io.Serial
import java.io.Serializable

/**
 * A Movie.
 */
@Entity
@Table(name = "movie")
class Movie : Serializable {
    @JvmField
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    var id: Long? = null

    @JvmField
    @Column(name = "name", nullable = false)
    var name: @NotNull String? = null

    @JvmField
    @Column(name = "origin_name", nullable = false)
    var originName: @NotNull String? = null

    @JvmField
    @Column(name = "is_completed", nullable = false)
    var isCompleted: @NotNull Boolean? = null

    @JvmField
    @Column(name = "slug", nullable = false)
    var slug: @NotNull String? = null

    @JvmField
    @Column(name = "episode_current", nullable = false)
    var episodeCurrent: @NotNull String? = null

    @JvmField
    @Column(name = "episode_total", nullable = false)
    var episodeTotal: @NotNull Int? = null

    @JvmField
    @Column(name = "quality", nullable = false)
    var quality: @NotNull String? = null

    @JvmField
    @Column(name = "year", nullable = false)
    var year: @NotNull Int? = null

    @JvmField
    @Column(name = "trailer_url")
    var trailerUrl: String? = null

    @JvmField
    @Column(name = "time")
    var time: String? = null

    @JvmField
    @Column(name = "content")
    var content: String? = null

    @JvmField
    @Column(name = "is_single", nullable = false)
    var isSingle: @NotNull Boolean? = null

    @JvmField
    @Column(name = "thumb_url")
    var thumbUrl: String? = null

    @JvmField
    @Column(name = "poster_url")
    var posterUrl: String? = null

    @JvmField
    @Column(name = "actors")
    var actors: String? = null

    @JvmField
    @Column(name = "country")
    var country: String? = null

    @JvmField
    @Column(name = "premium_only", nullable = false)
    var premiumOnly: @NotNull Boolean? = null

    @JvmField
    @set:JsonIgnoreProperties(value = ["movies"], allowSetters = true)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_movie__genres",
        joinColumns = [JoinColumn(name = "movie_id")],
        inverseJoinColumns = [JoinColumn(name = "genres_id")]
    )
    @JsonIgnoreProperties(value = ["movies"], allowSetters = true)
    var genres: MutableSet<Genres?> = HashSet()

    // jhipster-needle-entity-add-field - JHipster will add fields here
    fun id(id: Long?): Movie {
        this.id = id
        return this
    }

    fun name(name: String?): Movie {
        this.name = name
        return this
    }

    fun originName(originName: String?): Movie {
        this.originName = originName
        return this
    }

    fun isCompleted(isCompleted: Boolean?): Movie {
        this.isCompleted = isCompleted
        return this
    }

    fun slug(slug: String?): Movie {
        this.slug = slug
        return this
    }

    fun episodeCurrent(episodeCurrent: String?): Movie {
        this.episodeCurrent = episodeCurrent
        return this
    }

    fun episodeTotal(episodeTotal: Int?): Movie {
        this.episodeTotal = episodeTotal
        return this
    }

    fun quality(quality: String?): Movie {
        this.quality = quality
        return this
    }

    fun year(year: Int?): Movie {
        this.year = year
        return this
    }

    fun trailerUrl(trailerUrl: String?): Movie {
        this.trailerUrl = trailerUrl
        return this
    }

    fun time(time: String?): Movie {
        this.time = time
        return this
    }

    fun content(content: String?): Movie {
        this.content = content
        return this
    }

    fun isSingle(isSingle: Boolean?): Movie {
        this.isSingle = isSingle
        return this
    }

    fun thumbUrl(thumbUrl: String?): Movie {
        this.thumbUrl = thumbUrl
        return this
    }

    fun posterUrl(posterUrl: String?): Movie {
        this.posterUrl = posterUrl
        return this
    }

    fun actors(actors: String?): Movie {
        this.actors = actors
        return this
    }

    fun country(country: String?): Movie {
        this.country = country
        return this
    }

    fun premiumOnly(premiumOnly: Boolean?): Movie {
        this.premiumOnly = premiumOnly
        return this
    }

    fun genres(genres: MutableSet<Genres?>): Movie {
        this.genres = genres
        return this
    }

    fun addGenres(genres: Genres?): Movie {
        this.genres.add(genres)
        return this
    }

    fun removeGenres(genres: Genres?): Movie {
        this.genres.remove(genres)
        return this
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is Movie) {
            return false
        }
        return this.id != null && this.id == o.id
    }

    override fun hashCode(): Int {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return javaClass.hashCode()
    }

    // prettier-ignore
    override fun toString(): String {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", originName='" + originName + "'" +
                ", isCompleted='" + isCompleted + "'" +
                ", slug='" + slug + "'" +
                ", episodeCurrent='" + episodeCurrent + "'" +
                ", episodeTotal=" + episodeTotal +
                ", quality='" + quality + "'" +
                ", year=" + year +
                ", trailerUrl='" + trailerUrl + "'" +
                ", time='" + time + "'" +
                ", content='" + content + "'" +
                ", isSingle='" + isSingle + "'" +
                ", thumbUrl='" + thumbUrl + "'" +
                ", posterUrl='" + posterUrl + "'" +
                ", actors='" + actors + "'" +
                ", country='" + country + "'" +
                ", premiumOnly='" + premiumOnly + "'" +
                "}"
    }

    companion object {
        @Serial
        private val serialVersionUID = 1L
    }
}
