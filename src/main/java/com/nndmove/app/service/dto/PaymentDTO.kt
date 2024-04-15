package com.nndmove.app.service.dto

import jakarta.validation.constraints.NotNull
import java.io.Serializable
import java.util.*

/**
 * A DTO for the [com.nndmove.app.domain.Payment] entity.
 */
class PaymentDTO : Serializable {
    @JvmField
    var id: Long? = null

    @JvmField
    var paymentTime: @NotNull Long? = null

    @JvmField
    var paymentPrice: @NotNull Long? = null

    @JvmField
    var paymentMethod: @NotNull String? = null

    @JvmField
    var status: @NotNull String? = null

    @JvmField
    var transactionId: @NotNull String? = null

    @JvmField
    var user: UserDTO? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is PaymentDTO) {
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
        return "PaymentDTO{" +
                "id=" + id +
                ", paymentTime=" + paymentTime +
                ", paymentPrice=" + paymentPrice +
                ", paymentMethod='" + paymentMethod + "'" +
                ", status='" + status + "'" +
                ", transactionId='" + transactionId + "'" +
                ", user=" + user +
                "}"
    }
}
