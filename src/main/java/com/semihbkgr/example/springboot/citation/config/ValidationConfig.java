package com.semihbkgr.example.springboot.citation.config;

import com.semihbkgr.example.springboot.citation.validate.UserBlacklistValidator;
import com.semihbkgr.example.springboot.citation.validate.UserConstraintValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(ValidationConfig.UserValidationConfigProperties.class)
public class ValidationConfig {

    @Bean
    public UserConstraintValidator userConstraintValidator(UserValidationConfigProperties validationConfigProperties) {
        return new UserConstraintValidator(validationConfigProperties.getConstraints());
    }

    @Bean
    public UserBlacklistValidator userBlacklistValidator(UserValidationConfigProperties validationConfigProperties) {
        return new UserBlacklistValidator(new HashSet<>());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ConfigurationProperties(prefix = "validation.user")
    public static class UserValidationConfigProperties {

        private Map<String, Object> constraints;

        private String blacklist;

    }

}
