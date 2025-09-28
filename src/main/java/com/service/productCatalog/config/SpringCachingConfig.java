package com.service.productCatalog.config;

import com.service.productCatalog.constants.CacheNames;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class SpringCachingConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(
                CacheNames.PRODUCTS,
                CacheNames.ORDERS,
                CacheNames.BILLS,
                CacheNames.ITEMS,
                CacheNames.INVOICES
        );
    }
}
