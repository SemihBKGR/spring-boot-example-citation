package com.semihbkgr.example.springboot.citation.validate;

import com.semihbkgr.example.springboot.citation.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.Set;

class UserBlacklistValidatorTest {

    static final Set<String> INVALID_KEYWORDS = Set.of("admin", "user");

    UserBlacklistValidator validator;

    @BeforeEach
    void init() {
        validator = new UserBlacklistValidator(INVALID_KEYWORDS);
    }

    @Test
    @DisplayName("Validated user")
    void validateUser() {
        var user = new User();
        user.setUsername("username");
        var userMono = validator.validate(user, false);
        StepVerifier.create(userMono)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    @DisplayName("Inalidated user")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    void invalidateUser() {
        var user = new User();
        user.setUsername(INVALID_KEYWORDS.stream().findAny().get());
        var userMono = validator.validate(user, false);
        StepVerifier.create(userMono)
                .verifyError(BlacklistValidationException.class);
    }

    @Test
    @DisplayName("Validated user leniently")
    void validateUserLeniently() {
        var user = new User();
        user.setUsername("username");
        var userMono = validator.validate(user, true);
        StepVerifier.create(userMono)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    @DisplayName("Inalidated user leniently")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    void invalidateUserLeniently() {
        var user = new User();
        user.setUsername(INVALID_KEYWORDS.stream().findAny().get());
        var userMono = validator.validate(user, true);
        StepVerifier.create(userMono)
                .verifyError(BlacklistValidationException.class);
    }

}