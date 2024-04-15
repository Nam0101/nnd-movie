package com.nndmove.app.config

import jakarta.servlet.ServletContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.util.CollectionUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import tech.jhipster.config.JHipsterProperties

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
open class WebConfigurer(private val env: Environment, private val jHipsterProperties: JHipsterProperties) :
    ServletContextInitializer {
    private val log: Logger = LoggerFactory.getLogger(WebConfigurer::class.java)

    override fun onStartup(servletContext: ServletContext) {
        if (env.activeProfiles.size != 0) {
            log.info("Web application configuration, using profiles: {}", *env.activeProfiles as Array<Any?>)
        }

        log.info("Web application fully configured")
    }

    @Bean
    open fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = jHipsterProperties.cors
        if (!CollectionUtils.isEmpty(config.allowedOrigins) || !CollectionUtils.isEmpty(config.allowedOriginPatterns)) {
            log.debug("Registering CORS filter")
            source.registerCorsConfiguration("/api/**", config)
            source.registerCorsConfiguration("/management/**", config)
            source.registerCorsConfiguration("/v3/api-docs", config)
            source.registerCorsConfiguration("/swagger-ui/**", config)
        }
        return CorsFilter(source)
    }
}
