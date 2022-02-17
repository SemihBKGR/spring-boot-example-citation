package com.semihbkgr.example.springboot.tale.config;

import io.r2dbc.spi.ConnectionFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;

@Slf4j
@Configuration
@EnableConfigurationProperties(R2dbcConfig.R2dbcConfigProperties.class)
@EnableR2dbcAuditing(auditorAwareRef = "auditorAware")
@EnableR2dbcRepositories(basePackages = "com.semihbkgr.example.springboot.tale.model")
public class R2dbcConfig {

    @Bean
    public ConnectionFactoryInitializer connectionFactoryInitializer(ConnectionFactory connectionFactory, R2dbcConfigProperties configProperties) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        log.info("R2dbcConfigProperties enabled: {}", configProperties.isEnabled());
        if (configProperties.isEnabled()) {
            log.info("R2dbcConfigProperties populators: {}", (Object) configProperties.getPopulators());
            log.info("R2dbcConfigProperties cleaners: {}", (Object) configProperties.getCleaners());
            CompositeDatabasePopulator populators = new CompositeDatabasePopulator();
            for (var populator : configProperties.getPopulators())
                populators.addPopulators(new ResourceDatabasePopulator(new ClassPathResource(populator)));
            initializer.setDatabasePopulator(populators);
            CompositeDatabasePopulator cleaner = new CompositeDatabasePopulator();
            for (var populator : configProperties.getCleaners())
                cleaner.addPopulators(new ResourceDatabasePopulator(new ClassPathResource(populator)));
            initializer.setDatabaseCleaner(cleaner);
        }
        return initializer;
    }

    @Bean
    public ReactiveAuditorAware<Integer> auditorAware() {
        return () -> ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(SecurityConfig.SecurityUser.class::cast)
                .map(SecurityConfig.SecurityUser::getId);
    }


    @Setter
    @Getter
    @NoArgsConstructor
    @ConfigurationProperties(prefix = "sql.r2dbc")
    public static class R2dbcConfigProperties {

        private static final String[] EMPTY_STRING_ARRAY = new String[0];

        private boolean enabled = false;

        private String[] populators = EMPTY_STRING_ARRAY;

        private String[] cleaners = EMPTY_STRING_ARRAY;

    }

}
