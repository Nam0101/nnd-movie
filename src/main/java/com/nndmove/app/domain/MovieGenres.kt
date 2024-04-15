package com.nndmove.app.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.io.Serial
import java.io.Serializable

/**
 * A MovieGenres.
 */
@Entity
@Table(name = "movie_genres")
class MovieGenres : Serializable {
    @JvmField
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    var id: Long? = null

    @JvmField
    @set:JsonIgnoreProperties(value = ["genres"], allowSetters = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = ["genres"], allowSetters = true)
    var movie: Movie? = null

    @JvmField
    @set:JsonIgnoreProperties(value = ["movies"], allowSetters = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = ["movies"], allowSetters = true)
    var genres: Genres? = null

    // jhipster-needle-entity-add-field - JHipster will add fields here
    fun id(id: Long?): MovieGenres {
        this.id = id
        return this
    }

    fun movie(movie: Movie?): MovieGenres {
        this.movie = movie
        return this
    }

    fun genres(genres: Genres?): MovieGenres {
        this.genres = genres
        return this
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is MovieGenres) {
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
        return "MovieGenres{" +
                "id=" + id +
                "}"
    }

    companion object {
        @Serial
        private val serialVersionUID = 1L
    }
}
