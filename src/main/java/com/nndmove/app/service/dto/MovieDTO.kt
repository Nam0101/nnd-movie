package com.nndmove.app.service.dto

import jakarta.validation.constraints.NotNull
import java.io.Serializable
import java.util.*

/**
 * A DTO for the [com.nndmove.app.domain.Movie] entity.
 */
class MovieDTO : Serializable {
    @JvmField
    var id: Long? = null

    @JvmField
    var name: @NotNull String? = null

    @JvmField
    var originName: @NotNull String? = null

    @JvmField
    var isCompleted: @NotNull Boolean? = null

    @JvmField
    var slug: @NotNull String? = null

    @JvmField
    var episodeCurrent: @NotNull String? = null

    @JvmField
    var episodeTotal: @NotNull Int? = null

    @JvmField
    var quality: @NotNull String? = null

    @JvmField
    var year: @NotNull Int? = null

    @JvmField
    var trailerUrl: String? = null

    @JvmField
    var time: String? = null

    @JvmField
    var content: String? = null

    @JvmField
    var isSingle: @NotNull Boolean? = null

    @JvmField
    var thumbUrl: String? = null

    @JvmField
    var posterUrl: String? = null

    @JvmField
    var actors: String? = null

    @JvmField
    var country: String? = null

    @JvmField
    var premiumOnly: @NotNull Boolean? = null

    @JvmField
    var genres: Set<GenresDTO> = HashSet()

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is MovieDTO) {
            return false
        }

        if (this.id == null) {
            return false
        }
        return this.id == o.id
    }

    override fun hashCode(): Int {
        return Objects.hash(this.id)
    }

    // prettier-ignore
    override fun toString(): String {
        return "MovieDTO{" +
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
                ", genres=" + genres +
                "}"
    }
}
