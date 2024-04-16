package com.nndmove.app.web.rest

import com.nndmove.app.domain.User
import com.nndmove.app.repository.UserRepository
import com.nndmove.app.security.SecurityUtils
import com.nndmove.app.service.MailService
import com.nndmove.app.service.UserService
import com.nndmove.app.service.dto.AdminUserDTO
import com.nndmove.app.service.dto.PasswordChangeDTO
import com.nndmove.app.web.rest.errors.EmailAlreadyUsedException
import com.nndmove.app.web.rest.errors.InvalidPasswordException
import com.nndmove.app.web.rest.vm.KeyAndPasswordVM
import com.nndmove.app.web.rest.vm.ManagedUserVM
import jakarta.validation.Valid
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
open class AccountResource(
    private val userRepository: UserRepository,
    private val userService: UserService,
    private val mailService: MailService
) {
    private class AccountResourceException(message: String) : RuntimeException(message)

    private val log: Logger = LoggerFactory.getLogger(AccountResource::class.java)

    /**
     * `POST  /register` : register the user.
     *
     * @param managedUserVM the managed user View Model.
     * @throws InvalidPasswordException `400 (Bad Request)` if the password is incorrect.
     * @throws EmailAlreadyUsedException `400 (Bad Request)` if the email is already used.
     * @throws LoginAlreadyUsedException `400 (Bad Request)` if the login is already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerAccount(@RequestBody managedUserVM: @Valid ManagedUserVM?) {
        if (isPasswordLengthInvalid(managedUserVM!!.password)) {
            throw InvalidPasswordException()
        }
        val user = userService.registerUser(managedUserVM, managedUserVM.password)
        mailService.sendActivationEmail(user)
    }

    /**
     * `GET  /activate` : activate the registered user.
     *
     * @param key the activation key.
     * @throws RuntimeException `500 (Internal Server Error)` if the user couldn't be activated.
     */
    @GetMapping("/activate")
    fun activateAccount(@RequestParam(value = "key") key: String?) {
        val user = userService.activateRegistration(key)
        if (!user.isPresent) {
            throw AccountResourceException("No user was found for this activation key")
        }
    }

    @get:GetMapping("/account")
    val account: AdminUserDTO
        /**
         * `GET  /account` : get the current user.
         *
         * @return the current user.
         * @throws RuntimeException `500 (Internal Server Error)` if the user couldn't be returned.
         */
        get() = userService
            .userWithAuthorities
            .map { user: User? ->
                AdminUserDTO(
                    user!!
                )
            }
            .orElseThrow { AccountResourceException("User could not be found") }

    /**
     * `POST  /account` : update the current user information.
     *
     * @param userDTO the current user information.
     * @throws EmailAlreadyUsedException `400 (Bad Request)` if the email is already used.
     * @throws RuntimeException `500 (Internal Server Error)` if the user login wasn't found.
     */
    @PostMapping("/account")
    fun saveAccount(@RequestBody userDTO: @Valid AdminUserDTO?) {
        val userLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow { AccountResourceException("Current user login not found") }
        val existingUser = userRepository.findOneByEmailIgnoreCase(
            userDTO!!.email
        )
        if (existingUser.isPresent && (!existingUser.orElseThrow().getLogin().equals(userLogin, ignoreCase = true))) {
            throw EmailAlreadyUsedException()
        }
        val user = userRepository.findOneByLogin(userLogin)
        if (!user.isPresent) {
            throw AccountResourceException("User could not be found")
        }
        userService.updateUser(userDTO.firstName, userDTO.lastName, userDTO.email, userDTO.langKey, userDTO.imageUrl)
    }

    /**
     * `POST  /account/change-password` : changes the current user's password.
     *
     * @param passwordChangeDto current and new password.
     * @throws InvalidPasswordException `400 (Bad Request)` if the new password is incorrect.
     */
    @PostMapping(path = ["/account/change-password"])
    fun changePassword(@RequestBody passwordChangeDto: PasswordChangeDTO) {
        if (isPasswordLengthInvalid(passwordChangeDto.newPassword)) {
            throw InvalidPasswordException()
        }
        userService.changePassword(passwordChangeDto.currentPassword, passwordChangeDto.newPassword)
    }

    /**
     * `POST   /account/reset-password/init` : Send an email to reset the password of the user.
     *
     * @param mail the mail of the user.
     */
    @PostMapping(path = ["/account/reset-password/init"])
    fun requestPasswordReset(@RequestBody mail: String?) {
        val user = userService.requestPasswordReset(mail)
        if (user.isPresent) {
            mailService.sendPasswordResetMail(user.orElseThrow())
        } else {
            // Pretend the request has been successful to prevent checking which emails really exist
            // but log that an invalid attempt has been made
            log.warn("Password reset requested for non existing mail")
        }
    }

    /**
     * `POST   /account/reset-password/finish` : Finish to reset the password of the user.
     *
     * @param keyAndPassword the generated key and the new password.
     * @throws InvalidPasswordException `400 (Bad Request)` if the password is incorrect.
     * @throws RuntimeException `500 (Internal Server Error)` if the password could not be reset.
     */
    @PostMapping(path = ["/account/reset-password/finish"])
    fun finishPasswordReset(@RequestBody keyAndPassword: KeyAndPasswordVM) {
        if (isPasswordLengthInvalid(keyAndPassword.newPassword)) {
            throw InvalidPasswordException()
        }
        val user = userService.completePasswordReset(keyAndPassword.newPassword, keyAndPassword.key)

        if (!user.isPresent) {
            throw AccountResourceException("No user was found for this reset key")
        }
    }

    companion object {
        private fun isPasswordLengthInvalid(password: String?): Boolean {
            return (StringUtils.isEmpty(password) || password!!.length < ManagedUserVM.PASSWORD_MIN_LENGTH || password.length > ManagedUserVM.PASSWORD_MAX_LENGTH
                    )
        }
    }
}
