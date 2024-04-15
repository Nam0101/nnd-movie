package com.nndmove.app.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.io.Serial
import java.io.Serializable

/**
 * A Playlist.
 */
@Entity
@Table(name = "playlist")
class Playlist : Serializable {
    @JvmField
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    var id: Long? = null

    @JvmField
    @ManyToOne(fetch = FetchType.LAZY)
    var user: User? = null

    @JvmField
    @set:JsonIgnoreProperties(value = ["genres"], allowSetters = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = ["genres"], allowSetters = true)
    var movie: Movie? = null

    // jhipster-needle-entity-add-field - JHipster will add fields here
    fun id(id: Long?): Playlist {
        this.id = id
        return this
    }

    fun user(user: User?): Playlist {
        this.user = user
        return this
    }

    fun movie(movie: Movie?): Playlist {
        this.movie = movie
        return this
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is Playlist) {
            return false
        }
        return this.id != null && this.id == o.id
    }

    override fun hashCode(): Int {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return javaClass.hashCode()
    }

    // prettier-ignore
    override fun toString(): String {
        return "Playlist{" +
                "id=" + id +
                "}"
    }

    companion object {
        @Serial
        private val serialVersionUID = 1L
    }
}
