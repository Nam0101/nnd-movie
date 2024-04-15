package com.nndmove.app.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.Instant

/**
 * Base abstract class for entities which will hold definitions for created, last modified, created by,
 * last modified by attributes.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties(value = ["createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate"], allowGetters = true)
abstract class AbstractAuditingEntity<T> : Serializable {
    abstract val id: T

    @JvmField
    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    var createdBy: String? = null

    @JvmField
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    var createdDate: Instant = Instant.now()

    @JvmField
    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    var lastModifiedBy: String? = null

    @JvmField
    @LastModifiedDate
    @Column(name = "last_modified_date")
    var lastModifiedDate: Instant = Instant.now()

    companion object {
        private const val serialVersionUID = 1L
    }
}
