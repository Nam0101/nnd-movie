package com.nndmove.app.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.io.Serial
import java.io.Serializable
import java.util.function.Consumer

/**
 * A Genres.
 */
@Entity
@Table(name = "genres")
class Genres : Serializable {
    // jhipster-needle-entity-add-field - JHipster will add fields here
    @JvmField
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    var id: Long? = null

    @JvmField
    @Column(name = "genres", nullable = false)
    var genres: @NotNull String? = null

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "genres")
    @JsonIgnoreProperties(value = ["genres"], allowSetters = true)
    private var movies: MutableSet<Movie>? = HashSet()

    fun id(id: Long?): Genres {
        this.id = id
        return this
    }

    fun genres(genres: String?): Genres {
        this.genres = genres
        return this
    }

    fun getMovies(): Set<Movie>? {
        return this.movies
    }

    fun setMovies(movies: MutableSet<Movie>?) {
        if (this.movies != null) {
            this.movies!!.forEach(Consumer { i: Movie -> i.removeGenres(this) })
        }
        movies?.forEach(Consumer { i: Movie -> i.addGenres(this) })
        this.movies = movies
    }

    fun movies(movies: MutableSet<Movie>?): Genres {
        this.setMovies(movies)
        return this
    }

    fun addMovie(movie: Movie) {
        movies!!.add(movie)
        movie.genres.add(this)
    }

    fun removeMovie(movie: Movie) {
        movies!!.remove(movie)
        movie.genres.remove(this)
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is Genres) {
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
        return "Genres{" +
                "id=" + id +
                ", genres='" + genres + "'" +
                "}"
    }

    companion object {
        @Serial
        private val serialVersionUID = 1L
    }
}
