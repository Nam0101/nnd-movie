package com.nndmove.app.config

/**
 * Application constants.
 */
object Constants {
    // Regex for acceptable logins
    const val LOGIN_REGEX: String =
        "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$"

    const val SYSTEM: String = "system"
    const val DEFAULT_LANGUAGE: String = "vi"
    const val PASSWORD_REGEX: String = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])$"
}
