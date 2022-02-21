package com.semihbkgr.example.springboot.citation.controller;

import com.semihbkgr.example.springboot.citation.config.SecurityConfig;
import com.semihbkgr.example.springboot.citation.model.Authority;
import com.semihbkgr.example.springboot.citation.repository.AuthorityRepository;
import com.semihbkgr.example.springboot.citation.service.AuthorityServiceImpl;
import com.semihbkgr.example.springboot.citation.service.UserService;
import com.semihbkgr.example.springboot.citation.test.annotation.WithMockSecurityUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@WebFluxTest(controllers = AuthorityController.class)
@MockitoSettings(strictness = Strictness.WARN)
@Import({AuthorityServiceImpl.class, SecurityConfig.class})
class AuthorityControllerTest {

    @MockBean
    AuthorityRepository authorityRepository;

    @MockBean
    UserService userService;

    @Autowired
    WebTestClient webTestClient;

    @Test
    @DisplayName("Get")
    @WithMockSecurityUser
    void get() {

        var authority = new Authority();
        authority.setId(1);
        authority.setName("name");
        authority.setExplanation("explanation");

        when(authorityRepository.findAllBy(ArgumentMatchers.any()))
                .thenReturn(Flux.fromIterable(List.of(authority)));

        webTestClient.get()
                .uri("/authority")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<List<Authority>>() {
                });

    }

    @Test
    @DisplayName("Get when unauthenticated")
    void getWhenUnauthenticated() {

        var authority = new Authority();
        authority.setId(1);
        authority.setName("name");
        authority.setExplanation("explanation");

        webTestClient.get()
                .uri("/authority")
                .exchange()
                .expectStatus()
                .isUnauthorized();

    }

    @Test
    @DisplayName("Create")
    @WithMockSecurityUser(authorities = {"ROLE_ADMIN"})
    void create() {

        var saveAuthority = new Authority();
        saveAuthority.setName("name");
        saveAuthority.setExplanation("explanation");

        var savedAuthority = new Authority();
        savedAuthority.setId(1);
        savedAuthority.setName("name");
        savedAuthority.setExplanation("explanation");

        when(authorityRepository.save(saveAuthority))
                .thenReturn(Mono.just(savedAuthority));

        webTestClient.post()
                .uri("/authority")
                .body(BodyInserters.fromValue(saveAuthority))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Authority.class);

        verify(authorityRepository, times(1))
                .save(saveAuthority);

    }

    @Test
    @DisplayName("Create when unauthorized")
    @WithMockSecurityUser(authorities = {"ROLE_MODERATOR"})
    void createWhenUnauthorized() {

        var saveAuthority = new Authority();
        saveAuthority.setName("name");
        saveAuthority.setExplanation("explanation");

        webTestClient.post()
                .uri("/authority")
                .body(BodyInserters.fromValue(saveAuthority))
                .exchange()
                .expectStatus()
                .isForbidden();

    }


    @Test
    @DisplayName("Create when unauthenticated")
    void createWhenUnauthenticated() {

        var saveAuthority = new Authority();
        saveAuthority.setName("name");
        saveAuthority.setExplanation("explanation");

        webTestClient.post()
                .uri("/authority")
                .body(BodyInserters.fromValue(saveAuthority))
                .exchange()
                .expectStatus()
                .isUnauthorized();

    }

    @Test
    @DisplayName("Update")
    @WithMockSecurityUser(authorities = {"ROLE_ADMIN"})
    void update() {

        final var id = 0;

        var updateAuthority = new Authority();
        updateAuthority.setId(id);
        updateAuthority.setName("name");
        updateAuthority.setExplanation("explanation");

        var updatedAuthority = new Authority();
        updatedAuthority.setId(id);
        updatedAuthority.setName("name");
        updatedAuthority.setExplanation("explanation");

        when(authorityRepository.save(updateAuthority))
                .thenReturn(Mono.just(updatedAuthority));

        webTestClient.put()
                .uri("/authority/" + id)
                .body(BodyInserters.fromValue(updateAuthority))
                .exchange()
                .expectStatus()
                .isAccepted()
                .expectBody(Authority.class);

        verify(authorityRepository, times(1))
                .save(updateAuthority);

    }


    @Test
    @DisplayName("Update when unauthorized")
    @WithMockSecurityUser(authorities = {"ROLE_MODERATOR"})
    void updateWhenUnauthorized() {

        var updateAuthority = new Authority();
        updateAuthority.setName("name");
        updateAuthority.setExplanation("explanation");

        webTestClient.put()
                .uri("/authority/1")
                .body(BodyInserters.fromValue(updateAuthority))
                .exchange()
                .expectStatus()
                .isForbidden();

    }


    @Test
    @DisplayName("Update when unauthenticated")
    void updateWhenUnauthenticated() {

        var updateAuthority = new Authority();
        updateAuthority.setName("name");
        updateAuthority.setExplanation("explanation");

        webTestClient.put()
                .uri("/authority/1")
                .body(BodyInserters.fromValue(updateAuthority))
                .exchange()
                .expectStatus()
                .isUnauthorized();

    }

    @Test
    @DisplayName("Delete")
    @WithMockSecurityUser(authorities = {"ROLE_ADMIN"})
    void delete() {

        final var id = 1;

        when(authorityRepository.deleteById(id))
                .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/authority/" + id)
                .exchange()
                .expectStatus()
                .isAccepted();

        verify(authorityRepository, times(1))
                .deleteById(id);

    }

    @Test
    @DisplayName("Delete when unauthorized")
    @WithMockSecurityUser(authorities = {"ROLE_MODERATOR"})
    void deleteWhenUnauthorized() {
        webTestClient.delete()
                .uri("/authority/1")
                .exchange()
                .expectStatus()
                .isForbidden();
    }

    @Test
    @DisplayName("Delete when unauthenticated")
    void deleteWhenUnauthenticated() {
        webTestClient.delete()
                .uri("/authority/1")
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

}