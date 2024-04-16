package com.nndmove.app

import com.nndmove.app.config.ApplicationProperties
import com.nndmove.app.config.CRLFLogConverter
import jakarta.annotation.PostConstruct
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.core.env.Environment
import tech.jhipster.config.DefaultProfileUtil
import tech.jhipster.config.JHipsterConstants
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.*

@SpringBootApplication
@EnableConfigurationProperties(LiquibaseProperties::class, ApplicationProperties::class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
open class JhipsterApp(private val env: Environment) {
    /**
     * Initializes jhipster.
     *
     *
     * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
     *
     *
     * You can find more information on how profiles work with JHipster on [https://www.jhipster.tech/profiles/](https://www.jhipster.tech/profiles/).
     */
    @PostConstruct
    fun initApplication() {
        val activeProfiles: Collection<String> = Arrays.asList(*env.activeProfiles)
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) &&
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)
        ) {
            log.error(
                "You have misconfigured your application! It should not run " + "with both the 'dev' and 'prod' profiles at the same time."
            )
        }
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) &&
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)
        ) {
            log.error(
                "You have misconfigured your application! It should not " + "run with both the 'dev' and 'cloud' profiles at the same time."
            )
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(JhipsterApp::class.java)

        /**
         * Main method, used to run the application.
         *
         * @param args the command line arguments.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val app = SpringApplication(JhipsterApp::class.java)
            DefaultProfileUtil.addDefaultProfile(app)
            val env: Environment = app.run(*args).environment
            logApplicationStartup(env)
        }

        private fun logApplicationStartup(env: Environment) {
            val protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map { key: String? -> "https" }
                .orElse("http")
            val applicationName = env.getProperty("spring.application.name")
            val serverPort = env.getProperty("server.port")
            val contextPath = Optional.ofNullable(env.getProperty("server.servlet.context-path"))
                .filter { cs: String? -> StringUtils.isNotBlank(cs) }
                .orElse("/")
            var hostAddress: String? = "localhost"
            try {
                hostAddress = InetAddress.getLocalHost().hostAddress
            } catch (e: UnknownHostException) {
                log.warn("The host name could not be determined, using `localhost` as fallback")
            }
            log.info(
                CRLFLogConverter.CRLF_SAFE_MARKER,
                """

            ----------------------------------------------------------
            ${'\t'}Application '{}' is running! Access URLs:
            ${'\t'}Local: ${'\t'}${'\t'}{}://localhost:{}{}
            ${'\t'}External: ${'\t'}{}://{}:{}{}
            ${'\t'}Profile(s): ${'\t'}{}
            ----------------------------------------------------------
            """.trimIndent(),
                applicationName,
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                if (env.activeProfiles.isEmpty()) env.defaultProfiles else env.activeProfiles
            )
        }
    }
}
