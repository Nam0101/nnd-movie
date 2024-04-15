package com.nndmove.app.service.dto

import jakarta.validation.constraints.NotNull
import java.io.Serializable
import java.time.ZonedDateTime
import java.util.*

/**
 * A DTO for the [com.nndmove.app.domain.Premium] entity.
 */
class PremiumDTO : Serializable {
    @JvmField
    var id: Long? = null

    @JvmField
    var startTime: @NotNull ZonedDateTime? = null

    @JvmField
    var endTime: @NotNull ZonedDateTime? = null

    @JvmField
    var user: UserDTO? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is PremiumDTO) {
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
        return "PremiumDTO{" +
                "id=" + id +
                ", startTime='" + startTime + "'" +
                ", endTime='" + endTime + "'" +
                ", user=" + user +
                "}"
    }
}
