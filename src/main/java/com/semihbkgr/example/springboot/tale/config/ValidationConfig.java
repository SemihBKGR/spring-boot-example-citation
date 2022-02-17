package com.semihbkgr.example.springboot.tale.config;

import com.semihbkgr.example.springboot.tale.validate.UserValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableConfigurationProperties(ValidationConfig.UserValidationConfigProperties.class)
public class ValidationConfig {

    @Bean
    public UserValidator userValidator(UserValidationConfigProperties validationConfigProperties) {
        return new UserValidator(validationConfigProperties.getConstraints());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ConfigurationProperties(prefix = "validation.user")
    public static class UserValidationConfigProperties {

        private Map<String, Object> constraints;

    }

}
