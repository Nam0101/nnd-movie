package com.nndmove.app.service.dto

import jakarta.validation.constraints.NotNull
import java.io.Serializable
import java.util.*

/**
 * A DTO for the [com.nndmove.app.domain.Genres] entity.
 */
class GenresDTO : Serializable {
    @JvmField
    var id: Long? = null

    @JvmField
    var genres: @NotNull String? = null

    @JvmField
    var movies: Set<MovieDTO> = HashSet()

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is GenresDTO) {
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
        return "GenresDTO{" +
                "id=" + id +
                ", genres='" + genres + "'" +
                ", movies=" + movies +
                "}"
    }
}
