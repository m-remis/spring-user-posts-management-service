package com.michal.examples.user.posts.management.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author Michal Remis
 */
@Configuration
@EnableCaching
public class CacheConfig {

    public static final String FIND_POST_BY_ID_CACHE_NAME = "findPostById";
    public static final String FIND_USER_BY_ID_CACHE_NAME = "findUserById";

    @Bean
    public Caffeine<Object, Object> caffeineConfig(@Value("${spring.cache.ttl.default}") Duration cacheTtlDefault) {
        return Caffeine.newBuilder().expireAfterWrite(cacheTtlDefault);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
}
