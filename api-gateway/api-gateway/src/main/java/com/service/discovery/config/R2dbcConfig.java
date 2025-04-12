package com.service.discovery.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.mariadb.r2dbc.MariadbConnectionConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

import java.time.Duration;

@Configuration
public class R2dbcConfig {

    @Value("${spring.r2dbc.url}")
    String url;
    @Value("${spring.r2dbc.username}")
    String username;
    @Value("${spring.r2dbc.password}")
    String password;

    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {
        return new R2dbcEntityTemplate(connectionFactory);
    }

}
