package com.nndmove.app.config

import ch.qos.logback.classic.LoggerContext
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import tech.jhipster.config.JHipsterProperties
import tech.jhipster.config.logging.LoggingUtils

/*
* Configures the console and Logstash log appenders from the app properties
*/
@Configuration
open class LoggingConfiguration(
    @Value("\${spring.application.name}") appName: String,
    @Value("\${server.port}") serverPort: String,
    jHipsterProperties: JHipsterProperties,
    mapper: ObjectMapper
) {
    init {
        val context = LoggerFactory.getILoggerFactory() as LoggerContext

        val map: MutableMap<String, String> = HashMap()
        map["app_name"] = appName
        map["app_port"] = serverPort
        val customFields = mapper.writeValueAsString(map)

        val loggingProperties = jHipsterProperties.logging
        val logstashProperties = loggingProperties.logstash

        if (loggingProperties.isUseJsonFormat) {
            LoggingUtils.addJsonConsoleAppender(context, customFields)
        }
        if (logstashProperties.isEnabled) {
            LoggingUtils.addLogstashTcpSocketAppender(context, customFields, logstashProperties)
        }
        if (loggingProperties.isUseJsonFormat || logstashProperties.isEnabled) {
            LoggingUtils.addContextListener(context, customFields, loggingProperties)
        }
    }
}
