package com.nndmove.app.web.rest.vm

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * View Model object for storing a user's credentials.
 */
class LoginVM {
    @JvmField
    var username: @NotNull @Size(min = 1, max = 50) String? = null

    @JvmField
    var password: @NotNull @Size(min = 4, max = 100) String? = null

    var isRememberMe: Boolean = false

    // prettier-ignore
    override fun toString(): String {
        return "LoginVM{" +
                "username='" + username + '\'' +
                ", rememberMe=" + isRememberMe +
                '}'
    }
}
