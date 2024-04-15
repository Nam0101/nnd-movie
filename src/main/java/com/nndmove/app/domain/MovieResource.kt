package com.nndmove.app.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.io.Serial
import java.io.Serializable

/**
 * A MovieResource.
 */
@Entity
@Table(name = "movie_resource")
class MovieResource : Serializable {
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
    @Column(name = "link_embed", nullable = false)
    var linkEmbed: @NotNull String? = null

    @JvmField
    @Column(name = "link_m_3_u_8", nullable = false)
    var linkM3u8: @NotNull String? = null

    @JvmField
    @set:JsonIgnoreProperties(value = ["genres"], allowSetters = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = ["genres"], allowSetters = true)
    var movie: Movie? = null

    // jhipster-needle-entity-add-field - JHipster will add fields here
    fun id(id: Long?): MovieResource {
        this.id = id
        return this
    }

    fun part(part: Int?): MovieResource {
        this.part = part
        return this
    }

    fun linkEmbed(linkEmbed: String?): MovieResource {
        this.linkEmbed = linkEmbed
        return this
    }

    fun linkM3u8(linkM3u8: String?): MovieResource {
        this.linkM3u8 = linkM3u8
        return this
    }

    fun movie(movie: Movie?): MovieResource {
        this.movie = movie
        return this
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is MovieResource) {
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
        return "MovieResource{" +
                "id=" + id +
                ", part=" + part +
                ", linkEmbed='" + linkEmbed + "'" +
                ", linkM3u8='" + linkM3u8 + "'" +
                "}"
    }

    companion object {
        @Serial
        private val serialVersionUID = 1L
    }
}
