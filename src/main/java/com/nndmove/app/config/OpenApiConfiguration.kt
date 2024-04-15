package com.nndmove.app.config

import org.springdoc.core.models.GroupedOpenApi
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import tech.jhipster.config.JHipsterConstants
import tech.jhipster.config.JHipsterProperties
import tech.jhipster.config.apidoc.customizer.JHipsterOpenApiCustomizer

@Configuration
@Profile(JHipsterConstants.SPRING_PROFILE_API_DOCS)
open class OpenApiConfiguration {
    @Bean
    @ConditionalOnMissingBean(name = ["apiFirstGroupedOpenAPI"])
    open fun apiFirstGroupedOpenAPI(
        jhipsterOpenApiCustomizer: JHipsterOpenApiCustomizer?,
        jHipsterProperties: JHipsterProperties
    ): GroupedOpenApi {
        val properties = jHipsterProperties.apiDocs
        return GroupedOpenApi.builder()
            .group("openapi")
            .addOpenApiCustomizer(jhipsterOpenApiCustomizer)
            .packagesToScan(API_FIRST_PACKAGE)
            .pathsToMatch(*properties.defaultIncludePattern)
            .build()
    }

    companion object {
        const val API_FIRST_PACKAGE: String = "com.nndmove.app.web.api"
    }
}
