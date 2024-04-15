package com.nndmove.app.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.io.Serial
import java.io.Serializable

/**
 * A History.
 */
@Entity
@Table(name = "history")
class History : Serializable {
    // jhipster-needle-entity-add-field - JHipster will add fields here
    @JvmField
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    var id: Long? = null

    @JvmField
    @Column(name = "part", nullable = false)
    var part: @NotNull Int? = null

    @JvmField
    @Column(name = "stop_time", nullable = false)
    var stopTime: @NotNull Int? = null

    @JvmField
    @ManyToOne(fetch = FetchType.LAZY)
    var user: User? = null

    @JvmField
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = ["genres"], allowSetters = true)
    var movie: Movie? = null

    fun id(id: Long?): History {
        this.id = id
        return this
    }

    fun part(part: Int?): History {
        this.part = part
        return this
    }

    fun stopTime(stopTime: Int?): History {
        this.stopTime = stopTime
        return this
    }

    fun user(user: User?): History {
        this.user = user
        return this
    }

    fun movie(movie: Movie?): History {
        this.movie = movie
        return this
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is History) {
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
        return "History{" +
                "id=" + id +
                ", part=" + part +
                ", stopTime=" + stopTime +
                "}"
    }

    companion object {
        @Serial
        private val serialVersionUID = 1L
    }
}
