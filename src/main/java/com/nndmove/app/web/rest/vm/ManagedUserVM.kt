package com.nndmove.app.web.rest.vm

import com.nndmove.app.service.dto.AdminUserDTO
import jakarta.validation.constraints.Size

/**
 * View Model extending the AdminUserDTO, which is meant to be used in the user management UI.
 */
class ManagedUserVM : AdminUserDTO() {
    @JvmField
    var password: @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH) String? = null

    override fun toString(): String {
        return "ManagedUserVM{" + super.toString() + "} "
    }

    companion object {
        const val PASSWORD_MIN_LENGTH: Int = 4

        const val PASSWORD_MAX_LENGTH: Int = 100
    }
}
