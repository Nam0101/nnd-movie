package com.nndmove.app.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.io.Serial
import java.io.Serializable
import java.time.ZonedDateTime

/**
 * A Premium.
 */
@Entity
@Table(name = "premium")
class Premium : Serializable {
    @JvmField
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    var id: Long? = null

    @JvmField
    @Column(name = "start_time", nullable = false)
    var startTime: @NotNull ZonedDateTime? = null

    @JvmField
    @Column(name = "end_time", nullable = false)
    var endTime: @NotNull ZonedDateTime? = null

    @JvmField
    @ManyToOne(fetch = FetchType.LAZY)
    var user: User? = null

    // jhipster-needle-entity-add-field - JHipster will add fields here
    fun id(id: Long?): Premium {
        this.id = id
        return this
    }

    fun startTime(startTime: ZonedDateTime?): Premium {
        this.startTime = startTime
        return this
    }

    fun endTime(endTime: ZonedDateTime?): Premium {
        this.endTime = endTime
        return this
    }

    fun user(user: User?): Premium {
        this.user = user
        return this
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is Premium) {
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
        return "Premium{" +
                "id=" + id +
                ", startTime='" + startTime + "'" +
                ", endTime='" + endTime + "'" +
                "}"
    }

    companion object {
        @Serial
        private val serialVersionUID = 1L
    }
}
