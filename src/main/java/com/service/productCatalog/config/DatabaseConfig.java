package com.service.productCatalog.config;


import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "dbEntityManagerFactory",
        transactionManagerRef = "dbTransactionManager",
        basePackages = {"com.service.productCatalog.repo"}
)
public class DatabaseConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.db")
    public DataSourceProperties dbDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "dbDataSource")
    public DataSource dbDataSource() {
        return dbDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean dbEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dbDataSource())
                .packages("com.service.productCatalog.entity")
                .persistenceUnit("db")
                .build();
    }

    @Bean
    public PlatformTransactionManager dbTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(dbEntityManagerFactory(builder).getObject()));
    }
}