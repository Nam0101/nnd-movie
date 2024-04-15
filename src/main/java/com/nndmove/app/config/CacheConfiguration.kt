package com.nndmove.app.config

import com.nndmove.app.domain.*
import com.nndmove.app.repository.UserRepository
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.ehcache.jsr107.Eh107Configuration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer
import org.springframework.boot.info.BuildProperties
import org.springframework.boot.info.GitProperties
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.jhipster.config.JHipsterProperties
import tech.jhipster.config.cache.PrefixedKeyGenerator
import java.time.Duration
import javax.cache.CacheManager

@Configuration
@EnableCaching
open class CacheConfiguration(jHipsterProperties: JHipsterProperties) {
    private var gitProperties: GitProperties? = null
    private var buildProperties: BuildProperties? = null
    private val jcacheConfiguration: javax.cache.configuration.Configuration<Any, Any>

    init {
        val ehcache = jHipsterProperties.cache.ehcache

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Any::class.java,
                Any::class.java,
                ResourcePoolsBuilder.heap(ehcache.maxEntries)
            )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.timeToLiveSeconds.toLong())))
                .build()
        )
    }

    @Bean
    open fun cacheManagerCustomizer(): JCacheManagerCustomizer {
        return JCacheManagerCustomizer { cm: CacheManager ->
            createCache(cm, UserRepository.USERS_BY_LOGIN_CACHE)
            createCache(cm, UserRepository.USERS_BY_EMAIL_CACHE)
            createCache(cm, Authority::class.java.name)
            createCache(cm, Premium::class.java.name)
            createCache(cm, Payment::class.java.name)
            createCache(cm, Movie::class.java.name)
            createCache(cm, Movie::class.java.name + ".genres")
            createCache(cm, Genres::class.java.name)
            createCache(cm, Genres::class.java.name + ".movies")
            createCache(cm, MovieGenres::class.java.name)
            createCache(cm, MovieResource::class.java.name)
            createCache(cm, History::class.java.name)
            createCache(cm, Playlist::class.java.name)
        }
    }

    private fun createCache(cm: CacheManager, cacheName: String) {
        val cache = cm.getCache<Any, Any>(cacheName)
        cache?.clear()
            ?: cm.createCache(
                cacheName,
                jcacheConfiguration
            )
    }

    @Autowired(required = false)
    fun setGitProperties(gitProperties: GitProperties?) {
        this.gitProperties = gitProperties
    }

    @Autowired(required = false)
    fun setBuildProperties(buildProperties: BuildProperties?) {
        this.buildProperties = buildProperties
    }

    @Bean
    open fun keyGenerator(): KeyGenerator {
        return PrefixedKeyGenerator(this.gitProperties, this.buildProperties)
    }
}
