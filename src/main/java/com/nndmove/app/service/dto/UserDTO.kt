package com.nndmove.app.service.dto

import com.nndmove.app.domain.User
import java.io.Serializable
import java.time.Instant

/**
 * A DTO representing a user, with only the public attributes.
 */
class UserDTO : Serializable {
    @JvmField
    var id: Long? = null
    @JvmField
    var login: String? = null
    var isActivated: Boolean = false
    @JvmField
    var activationKey: String? = null
    @JvmField
    var createdBy: String? = null
    @JvmField
    var createdDate: Instant? = null
    @JvmField
    var email: String? = null
    @JvmField
    var firstName: String? = null
    @JvmField
    var imageUrl: String? = null
    @JvmField
    var langKey: String? = null
    @JvmField
    var lastModifiedBy: String? = null
    @JvmField
    var lastModifiedDate: Instant? = null
    @JvmField
    var lastName: String? = null
    var passwordHash: String? = null
    @JvmField
    var resetDate: Instant? = null
    @JvmField
    var resetKey: String? = null

    constructor(user: User) {
        this.id = user.id
        this.login = user.getLogin()
        // Add the new fields here
        this.isActivated = user.activated
        this.activationKey = user.activationKey
        this.createdBy = user.createdBy
        this.createdDate = user.createdDate
        this.email = user.email
        this.firstName = user.firstName
        this.imageUrl = user.imageUrl
        this.langKey = user.langKey
        this.lastModifiedBy = user.lastModifiedBy
        this.lastModifiedDate = user.lastModifiedDate
        this.lastName = user.lastName
        this.passwordHash = user.password
        this.resetDate = user.resetDate
        this.resetKey = user.resetKey
    }

    constructor()

    // prettier-ignore
    override fun toString(): String {
        return "UserDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", activated=" + isActivated +
                ", activationKey='" + activationKey + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", langKey='" + langKey + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", lastName='" + lastName + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", resetDate=" + resetDate +
                ", resetKey='" + resetKey + '\'' +
                "}"
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
