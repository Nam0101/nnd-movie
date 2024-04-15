package com.nndmove.app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import tech.jhipster.config.locale.AngularCookieLocaleResolver

@Configuration
open class LocaleConfiguration : WebMvcConfigurer {
    @Bean
    open fun localeResolver(): LocaleResolver {
        return AngularCookieLocaleResolver("NG_TRANSLATE_LANG_KEY")
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        val localeChangeInterceptor = LocaleChangeInterceptor()
        localeChangeInterceptor.paramName = "language"
        registry.addInterceptor(localeChangeInterceptor)
    }
}
