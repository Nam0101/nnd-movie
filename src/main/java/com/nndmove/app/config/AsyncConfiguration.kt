package com.nndmove.app.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.task.AsyncTaskExecutor
import org.springframework.core.task.support.TaskExecutorAdapter
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.concurrent.Executors

@Configuration
@EnableAsync
@EnableScheduling
@Profile("!testdev & !testprod")
open class AsyncConfiguration : AsyncConfigurer {
    private val log: Logger = LoggerFactory.getLogger(AsyncConfiguration::class.java)

    @Bean(name = ["taskExecutor"])
    override fun getAsyncExecutor(): AsyncTaskExecutor {
        log.debug("Creating Async Task Executor with vitual threads")
        val executorService = Executors.newVirtualThreadPerTaskExecutor()
        return TaskExecutorAdapter(executorService)
    }
    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler {
        // vitual threads do not throw exceptions
        return SimpleAsyncUncaughtExceptionHandler()
    }
}
