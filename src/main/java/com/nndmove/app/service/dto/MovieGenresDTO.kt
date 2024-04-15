package com.nndmove.app.service.dto

import java.io.Serializable
import java.util.*

/**
 * A DTO for the [com.nndmove.app.domain.MovieGenres] entity.
 */
class MovieGenresDTO : Serializable {
    @JvmField
    var id: Long? = null

    @JvmField
    var movie: MovieDTO? = null

    @JvmField
    var genres: GenresDTO? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is MovieGenresDTO) {
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
        return "MovieGenresDTO{" +
                "id=" + id +
                ", movie=" + movie +
                ", genres=" + genres +
                "}"
    }
}
