package com.nndmove.app.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Properties specific to JHipster.
 *
 *
 * Properties are configured in the `application.yml` file.
 * See [tech.jhipster.config.JHipsterProperties] for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
class ApplicationProperties {
    val liquibase: Liquibase = Liquibase()

    // jhipster-needle-application-properties-property
    // jhipster-needle-application-properties-property-getter
    class Liquibase {
        var asyncStart: Boolean? = null
    } // jhipster-needle-application-properties-property-class
}
