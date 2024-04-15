package com.nndmove.app.service.dto

import jakarta.validation.constraints.NotNull
import java.io.Serializable
import java.util.*

/**
 * A DTO for the [com.nndmove.app.domain.History] entity.
 */
class HistoryDTO : Serializable {
    @JvmField
    var id: Long? = null

    @JvmField
    var part: @NotNull Int? = null

    @JvmField
    var stopTime: @NotNull Int? = null

    @JvmField
    var user: UserDTO? = null

    @JvmField
    var movie: MovieDTO? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is HistoryDTO) {
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
        return "HistoryDTO{" +
                "id=" + id +
                ", part=" + part +
                ", stopTime=" + stopTime +
                ", user=" + user +
                ", movie=" + movie +
                "}"
    }
}
