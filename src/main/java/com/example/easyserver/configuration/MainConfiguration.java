package com.example.easyserver.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class MainConfiguration {

    @Bean
    public DataSource dataSource(
            final @Value("${database.url}") String url,
            final @Value("${database.username}") String username,
            final @Value("${database.password}") String password) {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
