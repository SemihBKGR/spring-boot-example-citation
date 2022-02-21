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
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties(ValidationConfig.UserValidationConfigProperties.class)
public class ValidationConfig {

    @Bean
    public UserConstraintValidator userConstraintValidator(UserValidationConfigProperties validationConfigProperties) {
        if (validationConfigProperties.getConstraints() != null)
            return new UserConstraintValidator(validationConfigProperties.getConstraints());
        return new UserConstraintValidator(Collections.emptyMap());
    }

    @Bean
    public UserBlacklistValidator userBlacklistValidator(UserValidationConfigProperties validationConfigProperties) {
        if (validationConfigProperties.getBlacklist() != null) {
            try (var is = new ClassPathResource(validationConfigProperties.getBlacklist()).getInputStream()) {
                var blacklistSet = Arrays.stream(new String(is.readAllBytes(), StandardCharsets.UTF_8).split("\n"))
                        .collect(Collectors.toSet());
                return new UserBlacklistValidator(blacklistSet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new UserBlacklistValidator(Collections.emptySet());
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
