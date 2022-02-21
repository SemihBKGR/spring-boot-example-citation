package com.semihbkgr.example.springboot.citation.validate;

import com.semihbkgr.example.springboot.citation.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.Map;

class UserConstraintValidatorTest {

    static final int USERNAME_MIN = 4;
    static final int USERNAME_MAX = 16;

    UserConstraintValidator validator;

    @BeforeEach
    void init() {
        validator = new UserConstraintValidator(Map
                .of("username-min", USERNAME_MIN, "username-max", USERNAME_MAX));
    }

    @Test
    @DisplayName("Validate user")
    void validateUser() {
        var user = new User();
        user.setUsername("username");
        user.setEmail("email");
        user.setPassword("password");
        var userMono = validator.validate(user, false);
        StepVerifier.create(userMono)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    @DisplayName("Invalidate user")
    void invalidateUser() {
        var user = new User();
        user.setUsername("usr");
        user.setEmail("email");
        user.setPassword("password");
        var userMono = validator.validate(user, false);
        StepVerifier.create(userMono)
                .verifyError(ConstraintValidationException.class);
    }

    @Test
    @DisplayName("Validate user leniently")
    void validateUserLeniently() {
        var user = new User();
        user.setUsername("username");
        user.setEmail("email");
        user.setPassword("password");
        var userMono = validator.validate(user, true);
        StepVerifier.create(userMono)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    @DisplayName("Invalidate user leniently")
    void invalidateUserLeniently() {
        var user = new User();
        user.setUsername("usr");
        user.setEmail("email");
        user.setPassword("password");
        var userMono = validator.validate(user, true);
        StepVerifier.create(userMono)
                .verifyError(ConstraintValidationException.class);
    }

    @Test
    @DisplayName("Validate user witr empty username leniently")
    void validateUserWithEmptyUsernameLeniently() {
        var user = new User();
        user.setUsername("");
        user.setEmail("email");
        user.setPassword("password");
        var userMono = validator.validate(user, true);
        StepVerifier.create(userMono)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    @DisplayName("Invalidate user with empty username")
    void invalidateUserWithEmptyUsername() {
        var user = new User();
        user.setUsername("");
        user.setEmail("email");
        user.setPassword("password");
        var userMono = validator.validate(user, false);
        StepVerifier.create(userMono)
                .verifyError(ConstraintValidationException.class);
    }

}