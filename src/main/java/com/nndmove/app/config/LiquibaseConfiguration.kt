package com.nndmove.app.config

import liquibase.integration.spring.SpringLiquibase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.env.Profiles
import tech.jhipster.config.JHipsterConstants
import tech.jhipster.config.liquibase.SpringLiquibaseUtil
import java.util.concurrent.Executor
import javax.sql.DataSource

@Configuration
open class LiquibaseConfiguration(private val env: Environment) {
    private val log: Logger = LoggerFactory.getLogger(LiquibaseConfiguration::class.java)

    @Value("\${application.liquibase.async-start:true}")
    private val asyncStart: Boolean? = null

    @Bean
    open fun liquibase(
        @Qualifier("taskExecutor") executor: Executor?,
        liquibaseProperties: LiquibaseProperties,
        @LiquibaseDataSource liquibaseDataSource: ObjectProvider<DataSource?>,
        dataSource: ObjectProvider<DataSource?>,
        dataSourceProperties: DataSourceProperties?
    ): SpringLiquibase {
        val liquibase = if (java.lang.Boolean.TRUE == asyncStart) {
            SpringLiquibaseUtil.createAsyncSpringLiquibase(
                this.env,
                executor,
                liquibaseDataSource.getIfAvailable(),
                liquibaseProperties,
                dataSource.getIfUnique(),
                dataSourceProperties
            )
        } else {
            SpringLiquibaseUtil.createSpringLiquibase(
                liquibaseDataSource.getIfAvailable(),
                liquibaseProperties,
                dataSource.getIfUnique(),
                dataSourceProperties
            )
        }
        liquibase.changeLog = "classpath:config/liquibase/master.xml"
        liquibase.contexts = liquibaseProperties.contexts
        liquibase.defaultSchema = liquibaseProperties.defaultSchema
        liquibase.liquibaseSchema = liquibaseProperties.liquibaseSchema
        liquibase.liquibaseTablespace = liquibaseProperties.liquibaseTablespace
        liquibase.databaseChangeLogLockTable = liquibaseProperties.databaseChangeLogLockTable
        liquibase.databaseChangeLogTable = liquibaseProperties.databaseChangeLogTable
        liquibase.isDropFirst = liquibaseProperties.isDropFirst
        liquibase.labelFilter = liquibaseProperties.labelFilter
        liquibase.setChangeLogParameters(liquibaseProperties.parameters)
        liquibase.setRollbackFile(liquibaseProperties.rollbackFile)
        liquibase.isTestRollbackOnUpdate = liquibaseProperties.isTestRollbackOnUpdate
        if (env.acceptsProfiles(Profiles.of(JHipsterConstants.SPRING_PROFILE_NO_LIQUIBASE))) {
            liquibase.setShouldRun(false)
        } else {
            liquibase.setShouldRun(liquibaseProperties.isEnabled)
            log.debug("Configuring Liquibase")
        }
        return liquibase
    }
}
