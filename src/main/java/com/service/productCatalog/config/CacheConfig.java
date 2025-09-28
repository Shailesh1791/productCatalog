package com.service.productCatalog.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Arrays;

//@Configuration
//@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(@Qualifier("caffeine1") Cache<Object, Object> caffeine1,
                                     @Qualifier("caffeine2") Cache<Object, Object> caffeine2) {
        CaffeineCache productCache = new CaffeineCache("products", caffeine1);
        CaffeineCache orderCache = new CaffeineCache("orders", caffeine2);
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(productCache, orderCache));
        return cacheManager;
    }

    @Bean("caffeine1")
    public Cache<Object, Object> caffeineProductCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(500)
                .expireAfterWrite(Duration.ofMinutes(5)).build();
    }

    @Bean("caffeine2")
    public Cache<Object, Object> caffeineOrderCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(500)
                .expireAfterWrite(Duration.ofMinutes(5)).build();
    }
}
