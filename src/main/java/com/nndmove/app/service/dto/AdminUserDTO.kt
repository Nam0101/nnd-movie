package com.nndmove.app.service.dto

import com.nndmove.app.config.Constants
import com.nndmove.app.domain.Authority
import com.nndmove.app.domain.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.io.Serializable
import java.time.Instant
import java.util.stream.Collectors

/**
 * A DTO representing a user, with his authorities.
 */
open class AdminUserDTO : Serializable {
    @JvmField
    var id: Long? = null

    @JvmField
    var login: @NotBlank @Pattern(regexp = Constants.LOGIN_REGEX) @Size(min = 1, max = 50) String? = null

    @JvmField
    var firstName: @Size(max = 50) String? = null

    @JvmField
    var lastName: @Size(max = 50) String? = null

    @JvmField
    var email: @Email @Size(min = 5, max = 254) String? = null

    @JvmField
    var imageUrl: @Size(max = 256) String? = null

    var isActivated: Boolean = false

    @JvmField
    var langKey: @Size(min = 2, max = 10) String? = null

    var createdBy: String? = null

    var createdDate: Instant? = null

    var lastModifiedBy: String? = null

    var lastModifiedDate: Instant? = null

    @JvmField
    var authorities: Set<String>? = null

    constructor(user: User) {
        this.id = user.id
        this.login = user.getLogin()
        this.firstName = user.firstName
        this.lastName = user.lastName
        this.email = user.email
        this.isActivated = user.activated
        this.imageUrl = user.imageUrl
        this.langKey = user.langKey
        this.createdBy = user.createdBy
        this.createdDate = user.createdDate
        this.lastModifiedBy = user.lastModifiedBy
        this.lastModifiedDate = user.lastModifiedDate
        this.authorities = user.authorities.stream().map { obj: Authority -> obj.name }
            .collect(Collectors.toSet()) as Set<String>?
    }

    constructor()

    // prettier-ignore
    override fun toString(): String {
        return "AdminUserDTO{" +
                "login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", activated=" + isActivated +
                ", langKey='" + langKey + '\'' +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", authorities=" + authorities +
                "}"
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
