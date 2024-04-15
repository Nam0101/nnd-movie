package com.nndmove.app.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.io.Serial
import java.io.Serializable

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
class Payment : Serializable {
    @JvmField
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    var id: Long? = null

    @JvmField
    @Column(name = "payment_time", nullable = false)
    var paymentTime: @NotNull Long? = null

    @JvmField
    @Column(name = "payment_price", nullable = false)
    var paymentPrice: @NotNull Long? = null

    @JvmField
    @Column(name = "payment_method", nullable = false)
    var paymentMethod: @NotNull String? = null

    @JvmField
    @Column(name = "status", nullable = false)
    var status: @NotNull String? = null

    @JvmField
    @Column(name = "transaction_id", nullable = false)
    var transactionId: @NotNull String? = null

    @JvmField
    @ManyToOne(fetch = FetchType.LAZY)
    var user: User? = null

    // jhipster-needle-entity-add-field - JHipster will add fields here
    fun id(id: Long?): Payment {
        this.id = id
        return this
    }

    fun paymentTime(paymentTime: Long?): Payment {
        this.paymentTime = paymentTime
        return this
    }

    fun paymentPrice(paymentPrice: Long?): Payment {
        this.paymentPrice = paymentPrice
        return this
    }

    fun paymentMethod(paymentMethod: String?): Payment {
        this.paymentMethod = paymentMethod
        return this
    }

    fun status(status: String?): Payment {
        this.status = status
        return this
    }

    fun transactionId(transactionId: String?): Payment {
        this.transactionId = transactionId
        return this
    }

    fun user(user: User?): Payment {
        this.user = user
        return this
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is Payment) {
            return false
        }
        return (id != null) && this.id == o.id
    }

    override fun hashCode(): Int {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return javaClass.hashCode()
    }

    // prettier-ignore
    override fun toString(): String {
        return "Payment{" +
                "id=" + id +
                ", paymentTime=" + paymentTime +
                ", paymentPrice=" + paymentPrice +
                ", paymentMethod='" + paymentMethod + "'" +
                ", status='" + status + "'" +
                ", transactionId='" + transactionId + "'" +
                "}"
    }

    companion object {
        @Serial
        private val serialVersionUID = 1L
    }
}
