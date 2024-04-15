package com.nndmove.app.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.zalando.problem.jackson.ProblemModule
import org.zalando.problem.violations.ConstraintViolationProblemModule
import java.text.SimpleDateFormat
import java.util.*

@Configuration
open class JacksonConfiguration {
    /**
     * Support for Java date and time API.
     *
     * @return the corresponding Jackson module.
     */
    @Bean
    open fun javaTimeModule(): JavaTimeModule {
        return JavaTimeModule()
    }

    @Bean
    open fun jdk8TimeModule(): Jdk8Module {
        return Jdk8Module()
    }

    /*
     * Support for Hibernate types in Jackson.
     */
    @Bean
    open fun hibernate5Module(): Hibernate5Module {
        return Hibernate5Module()
    }

    /*
     * Module for serialization/deserialization of RFC7807 Problem.
     */
    @Bean
    open fun problemModule(): ProblemModule {
        return ProblemModule()
    }

    /*
     * Module for serialization/deserialization of ConstraintViolationProblem.
     */
    @Bean
    open fun constraintViolationProblemModule(): ConstraintViolationProblemModule {
        return ConstraintViolationProblemModule()
    }

    @Bean
    open fun jackson2ObjectMapperBuilder(): Jackson2ObjectMapperBuilder {
        val builder = Jackson2ObjectMapperBuilder()
        builder.serializationInclusion(JsonInclude.Include.NON_NULL)
        builder.dateFormat(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))
        builder.timeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"))
        return builder
    }

    @Bean
    open fun jacksonObjectMapperCustomization(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { jacksonObjectMapperBuilder: Jackson2ObjectMapperBuilder ->
            jacksonObjectMapperBuilder.timeZone(
                TimeZone.getTimeZone("Asia/Ho_Chi_Minh")
            ).simpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        }
    }
}
