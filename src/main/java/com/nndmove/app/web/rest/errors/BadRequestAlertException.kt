package com.nndmove.app.web.rest.errors

import lombok.Getter
import org.springframework.http.HttpStatus
import org.springframework.web.ErrorResponseException
import tech.jhipster.web.rest.errors.ProblemDetailWithCause
import tech.jhipster.web.rest.errors.ProblemDetailWithCause.ProblemDetailWithCauseBuilder
import java.io.Serial
import java.net.URI

@Getter
// Inheritance tree of classes should not be too deep
open class BadRequestAlertException(
    type: URI?,
    defaultMessage: String?,
    private val entityName: String?,
    private val errorKey: String
) : ErrorResponseException(
    HttpStatus.BAD_REQUEST,
    ProblemDetailWithCauseBuilder.instance()
        .withStatus(HttpStatus.BAD_REQUEST.value())
        .withType(type)
        .withTitle(defaultMessage)
        .withProperty("message", "error.$errorKey")
        .withProperty("params", entityName)
        .build(),
    null
) {
    constructor(defaultMessage: String?, entityName: String?, errorKey: String) : this(
        ErrorConstants.DEFAULT_TYPE,
        defaultMessage,
        entityName,
        errorKey
    )

    constructor(entityName: String?, errorKey: String) : this(
        ErrorConstants.DEFAULT_TYPE,
        null,
        entityName,
        errorKey
    )
    //constructor with no arguments
    constructor() : this(null, null, null, null.toString())

    fun getErrorKey(): String {
        return errorKey
    }

    fun getEntityName(): String? {
        return entityName
    }

    val problemDetailWithCause: ProblemDetailWithCause
        get() = body as ProblemDetailWithCause

    companion object {
        @Serial
        private val serialVersionUID = 1L
    }
}
