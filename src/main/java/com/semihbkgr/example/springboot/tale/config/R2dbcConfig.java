package com.semihbkgr.example.springboot.tale.config;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;

@Slf4j
@Configuration
@EnableConfigurationProperties(R2dbcConfig.R2dbcConfigProperties.class)
@EnableR2dbcRepositories(basePackages = "com.semihbkgr.example.springboot.tale.model")
public class R2dbcConfig {

    @Bean
    public ConnectionFactoryInitializer connectionFactoryInitializer(ConnectionFactory connectionFactory, R2dbcConfigProperties configProperties) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        return initializer;
    }

    @ConfigurationProperties(prefix = "sql")
    public static class R2dbcConfigProperties {

    }

}
