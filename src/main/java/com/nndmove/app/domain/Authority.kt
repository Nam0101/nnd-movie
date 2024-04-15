package com.nndmove.app.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.data.domain.Persistable
import java.io.Serial
import java.io.Serializable
import java.util.*

/**
 * A Authority.
 */
@Entity
@Table(name = "movie_authority")
@JsonIgnoreProperties(value = ["new", "id"])
class Authority : Serializable, Persistable<String?> {
    @JvmField
    @Id
    @Column(name = "name", length = 50, nullable = false)
    var name: @NotNull @Size(max = 50) String? = null

    @Transient
    private var isPersisted = false

    // jhipster-needle-entity-add-field - JHipster will add fields here
    fun name(name: String?): Authority {
        this.name = name
        return this
    }

    @PostLoad
    @PostPersist
    fun updateEntityState() {
        this.setIsPersisted()
    }

    fun getName(): String? {
        return name
    }
    override fun getId(): String {
        return name!!
    }

    @Transient
    override fun isNew(): Boolean {
        return !this.isPersisted
    }

    fun setIsPersisted() {
        this.isPersisted = true
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is Authority) {
            return false
        }
        return (name != null) && this.name == o.name
    }

    override fun hashCode(): Int {
        return Objects.hashCode(name)
    }

    // prettier-ignore
    override fun toString(): String {
        return "Authority{" +
                "name=" + name +
                "}"
    }

    companion object {
        @Serial
        private val serialVersionUID = 1L
    }
}
