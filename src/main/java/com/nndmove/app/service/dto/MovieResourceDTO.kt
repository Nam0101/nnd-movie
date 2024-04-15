package com.nndmove.app.service.dto

import jakarta.validation.constraints.NotNull
import java.io.Serializable
import java.util.*

/**
 * A DTO for the [com.nndmove.app.domain.MovieResource] entity.
 */
class MovieResourceDTO : Serializable {
    @JvmField
    var id: Long? = null

    @JvmField
    var part: @NotNull Int? = null

    @JvmField
    var linkEmbed: @NotNull String? = null

    @JvmField
    var linkM3u8: @NotNull String? = null

    @JvmField
    var movie: MovieDTO? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is MovieResourceDTO) {
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
        return "MovieResourceDTO{" +
                "id=" + id +
                ", part=" + part +
                ", linkEmbed='" + linkEmbed + "'" +
                ", linkM3u8='" + linkM3u8 + "'" +
                ", movie=" + movie +
                "}"
    }
}
