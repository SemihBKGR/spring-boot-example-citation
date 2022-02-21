package com.semihbkgr.example.springboot.citation.controller;

import com.semihbkgr.example.springboot.citation.config.SecurityConfig;
import com.semihbkgr.example.springboot.citation.config.ValidationConfig;
import com.semihbkgr.example.springboot.citation.model.User;
import com.semihbkgr.example.springboot.citation.repository.UserRepository;
import com.semihbkgr.example.springboot.citation.service.UserServiceImpl;
import com.semihbkgr.example.springboot.citation.test.annotation.WithMockSecurityUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@WebFluxTest(controllers = UserController.class)
@MockitoSettings(strictness = Strictness.WARN)
@Import({UserServiceImpl.class, SecurityConfig.class, ValidationConfig.class})
class UserControllerTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    WebTestClient webTestClient;

    @Test
    @WithMockSecurityUser(id = 1)
    @DisplayName("Get")
    void get() {

        var user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setEmail("email");
        user.setPassword("password");
        user.setFirstname("firstname");
        user.setLastname("lastname");

        when(userRepository.findById(1))
                .thenReturn(Mono.just(user));

        webTestClient.get()
                .uri("/user")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(User.class);

        verify(userRepository, times(1))
                .findById(1);

    }

    @Test
    @DisplayName("Get when unauthenticated")
    void getWhenUnauthenticated() {

        webTestClient.get()
                .uri("/user")
                .exchange()
                .expectStatus()
                .isUnauthorized();

    }

    @Test
    @WithMockSecurityUser
    @DisplayName("Get by id")
    void getById() {

        final var id = 1;

        var user = new User();
        user.setId(id);
        user.setUsername("username");
        user.setEmail("email");
        user.setPassword("password");
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setAuthoritySet(Collections.emptySet());

        when(userRepository.findById(id))
                .thenReturn(Mono.just(user));

        webTestClient.get()
                .uri("/user/" + id)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(User.class);

        verify(userRepository, times(1))
                .findById(id);

    }

    @Test
    @DisplayName("Get by id when unauthenticated")
    void getByIdWhenUnauthenticated() {

        final var id = 1;

        webTestClient.get()
                .uri("/user/" + id)
                .exchange()
                .expectStatus()
                .isUnauthorized();

    }

    @Test
    @WithMockSecurityUser
    @DisplayName("Get by username")
    void getByUsername() {

        final var username = "username";

        var user = new User();
        user.setId(1);
        user.setUsername(username);
        user.setEmail("email");
        user.setPassword("password");
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setAuthoritySet(Collections.emptySet());

        when(userRepository.findByUsername(username))
                .thenReturn(Mono.just(user));

        webTestClient.get()
                .uri("/user/username/" + username)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(User.class);

        verify(userRepository, times(1))
                .findByUsername(username);

    }

    @Test
    @DisplayName("Get by username when unauthenticated")
    void getByUsernameWhenUnauthenticated() {

        final var username = "username";

        webTestClient.get()
                .uri("/user/username/" + username)
                .exchange()
                .expectStatus()
                .isUnauthorized();

    }


    @Test
    @DisplayName("SignUp")
    void signup() {

        var signupUser = new User();
        signupUser.setUsername("newuser");
        signupUser.setEmail("email");
        signupUser.setPassword("password");
        signupUser.setFirstname("firstname");
        signupUser.setLastname("lastname");

        var signedupUser = new User();
        signedupUser.setId(1);
        signedupUser.setUsername("newuser");
        signedupUser.setEmail("email");
        signedupUser.setPassword("password");
        signedupUser.setFirstname("firstname");
        signedupUser.setLastname("lastname");
        signedupUser.setAuthoritySet(Collections.emptySet());

        when(userRepository.save(signupUser))
                .thenReturn(Mono.just(signedupUser));

        webTestClient.post()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(signupUser))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(User.class);

        verify(userRepository, times(1))
                .save(signupUser);

    }

    @Test
    @DisplayName("SignUp with invalid credential constraint violation")
    void signupWithInvalidCredentialConstraintViolation() {

        var signupUser = new User();
        signupUser.setUsername("user");
        signupUser.setEmail("email");
        signupUser.setPassword("password");
        signupUser.setFirstname("firstname");
        signupUser.setLastname("lastname");

        webTestClient.post()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(signupUser))
                .exchange()
                .expectStatus()
                .isBadRequest();

    }

    @Test
    @DisplayName("SignUp with invalid credential blacklist violation")
    void signupWithInvalidCredentialBlacklistViolation() {

        var signupUser = new User();
        signupUser.setUsername("admin");
        signupUser.setEmail("email");
        signupUser.setPassword("password");
        signupUser.setFirstname("firstname");
        signupUser.setLastname("lastname");

        webTestClient.post()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(signupUser))
                .exchange()
                .expectStatus()
                .isBadRequest();

    }

    @Test
    @DisplayName("Update")
    @WithMockSecurityUser(id = 1)
    void update() {

        var updateUser = new User();
        updateUser.setId(1);
        updateUser.setUsername("updated-username");
        updateUser.setEmail("updated-email");
        updateUser.setPassword("updates-password");
        updateUser.setFirstname("updated-firstname");
        updateUser.setLastname("updated-lastname");

        var updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setUsername("updated-username");
        updatedUser.setEmail("updated-email");
        updatedUser.setPassword("updates-password");
        updatedUser.setFirstname("updated-firstname");
        updatedUser.setLastname("updated-lastname");
        updatedUser.setAuthoritySet(Collections.emptySet());

        when(userRepository.save(updateUser))
                .thenReturn(Mono.just(updatedUser));

        webTestClient.put()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updateUser))
                .exchange()
                .expectStatus()
                .isAccepted()
                .expectBody(User.class);

        verify(userRepository, times(1))
                .save(updateUser);

    }

    @Test
    @DisplayName("Update with invalid credential constraint violation")
    @WithMockSecurityUser(id = 1)
    void updateWithInvalidCredentialConstraintViolation() {

        var updateUser = new User();
        updateUser.setId(1);
        updateUser.setUsername("name");
        updateUser.setEmail("updated-email");
        updateUser.setPassword("updates-password");
        updateUser.setFirstname("updated-firstname");
        updateUser.setLastname("updated-lastname");

        webTestClient.put()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updateUser))
                .exchange()
                .expectStatus()
                .isBadRequest();

    }

    @Test
    @DisplayName("Update with invalid credential blacklist violation")
    @WithMockSecurityUser(id = 1)
    void updateWithInvalidCredentialBacklistViolation() {

        var updateUser = new User();
        updateUser.setId(1);
        updateUser.setUsername("admin");
        updateUser.setEmail("updated-email");
        updateUser.setPassword("updates-password");
        updateUser.setFirstname("updated-firstname");
        updateUser.setLastname("updated-lastname");

        webTestClient.put()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updateUser))
                .exchange()
                .expectStatus()
                .isBadRequest();

    }

    @Test
    @DisplayName("Update when unauthenticated")
    void updateWhenUnauthenticated() {

        var updateUser = new User();
        updateUser.setId(1);
        updateUser.setUsername("updated-username");
        updateUser.setEmail("updated-email");
        updateUser.setPassword("updates-password");
        updateUser.setFirstname("updated-firstname");
        updateUser.setLastname("updated-lastname");

        webTestClient.put()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updateUser))
                .exchange()
                .expectStatus()
                .isUnauthorized();

    }

}