package com.nndmove.app.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.nndmove.app.config.Constants
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.apache.commons.lang3.StringUtils
import org.hibernate.annotations.BatchSize
import java.io.Serial
import java.io.Serializable
import java.time.Instant
import java.util.*

/**
 * A user.
 */
@Entity
@Table(name = "movie_user")
class User : AbstractAuditingEntity<Long?>(), Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    override var id: Long? = null

    @Column(length = 50, unique = true, nullable = false)
    private var login: @NotNull @Pattern(regexp = Constants.LOGIN_REGEX) @Size(min = 1, max = 50) String? = null

    @JvmField
    @JsonIgnore
    @Column(name = "password_hash", length = 60, nullable = false)
    var password: @NotNull @Size(min = 60, max = 60) String? = null

    @JvmField
    @Column(name = "first_name", length = 50)
    var firstName: @Size(max = 50) String? = null

    @JvmField
    @Column(name = "last_name", length = 50)
    var lastName: @Size(max = 50) String? = null

    @JvmField
    @Column(length = 254, unique = true)
    var email: @Email @Size(min = 5, max = 254) String? = null

    @Column(nullable = false)
    var activated: @NotNull Boolean = false

    @JvmField
    @Column(name = "lang_key", length = 10)
    var langKey: @Size(min = 2, max = 10) String? = null

    @JvmField
    @Column(name = "image_url", length = 256)
    var imageUrl: @Size(max = 256) String? = null

    @JvmField
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    var activationKey: @Size(max = 20) String? = null

    @JvmField
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    var resetKey: @Size(max = 20) String? = null

    @JvmField
    @Column(name = "reset_date")
    var resetDate: Instant? = null

    @JvmField
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "movie_user_authority",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "authority_name", referencedColumnName = "name")]
    )
    @BatchSize(size = 20)
    var authorities: Set<Authority> = HashSet()

    // Lowercase the login before saving it in database
    fun setLogin(login: String?) {
        this.login = StringUtils.lowerCase(login, Locale.ENGLISH)
    }



    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is User) {
            return false
        }
        return id != null && id == o.id
    }
    fun setAuthorities(authorities: Set<Authority>) {
        this.authorities = authorities
    }

    override fun hashCode(): Int {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return javaClass.hashCode()
    }

    // prettier-ignore
    override fun toString(): String {
        return "User{" +
                "login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", activated='" + activated + '\'' +
                ", langKey='" + langKey + '\'' +
                ", activationKey='" + activationKey + '\'' +
                "}"
    }

    fun getLogin(): @NotNull @Pattern(regexp = Constants.LOGIN_REGEX) @Size(min = 1, max = 50) String? {
        return this.login
    }

    companion object {
        @Serial
        private val serialVersionUID = 1L
    }
}
