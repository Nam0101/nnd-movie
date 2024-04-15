package com.nndmove.app.service.dto

import java.io.Serializable

/**
 * A DTO representing a password change required data - current and new password.
 */
class PasswordChangeDTO : Serializable {
    @JvmField
    var currentPassword: String? = null
    @JvmField
    var newPassword: String? = null

    constructor(currentPassword: String?, newPassword: String?) {
        this.currentPassword = currentPassword
        this.newPassword = newPassword
    }

    constructor()

    companion object {
        private const val serialVersionUID = 1L
    }
}
