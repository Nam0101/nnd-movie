package com.nndmove.app.config

import com.nimbusds.jose.jwk.source.ImmutableSecret
import com.nimbusds.jose.util.Base64
import com.nndmove.app.management.SecurityMetersService
import com.nndmove.app.security.SecurityUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Configuration
open class SecurityJwtConfiguration {
    private val log: Logger = LoggerFactory.getLogger(SecurityJwtConfiguration::class.java)

    @Value("\${jhipster.security.authentication.jwt.base64-secret}")
    private val jwtKey: String? = null

    @Bean
    open fun jwtDecoder(metersService: SecurityMetersService): JwtDecoder {
        val jwtDecoder = NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(SecurityUtils.JWT_ALGORITHM).build()
        return JwtDecoder { token: String? ->
            try {
                return@JwtDecoder jwtDecoder.decode(token)
            } catch (e: Exception) {
                if (e.message!!.contains("Invalid signature")) {
                    metersService.trackTokenInvalidSignature()
                } else if (e.message!!.contains("Jwt expired at")) {
                    metersService.trackTokenExpired()
                } else if (e.message!!.contains("Invalid JWT serialization") ||
                    e.message!!.contains("Malformed token") ||
                    e.message!!.contains("Invalid unsecured/JWS/JWE")
                ) {
                    metersService.trackTokenMalformed()
                } else {
                    log.error("Unknown JWT error {}", e.message)
                }
                throw e
            }
        }
    }

    @Bean
    open fun jwtEncoder(): JwtEncoder {
        return NimbusJwtEncoder(ImmutableSecret(secretKey))
    }

    @Bean
    open fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val grantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
        grantedAuthoritiesConverter.setAuthorityPrefix("")
        grantedAuthoritiesConverter.setAuthoritiesClaimName(SecurityUtils.AUTHORITIES_KEY)

        val jwtAuthenticationConverter = JwtAuthenticationConverter()
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter)
        return jwtAuthenticationConverter
    }

    private val secretKey: SecretKey
        get() {
            val keyBytes = Base64.from(jwtKey).decode()
            return SecretKeySpec(keyBytes, 0, keyBytes.size, SecurityUtils.JWT_ALGORITHM.getName())
        }
}
