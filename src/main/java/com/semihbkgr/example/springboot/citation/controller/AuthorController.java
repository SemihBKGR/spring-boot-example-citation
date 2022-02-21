package com.semihbkgr.example.springboot.citation.controller;

import com.semihbkgr.example.springboot.citation.config.SecurityConfig;
import com.semihbkgr.example.springboot.citation.model.Author;
import com.semihbkgr.example.springboot.citation.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/{id}")
    public Mono<Author> get(@PathVariable int id) {
        return authorService.find(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Author> create(@RequestBody Author author) {
        return authorService.save(author);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Author> update(@PathVariable int id, @RequestBody Author author) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(SecurityConfig.SecurityUser.class::cast)
                .flatMap(securityUser -> {
                    author.setId(id);
                    return authorService.save(author);
                });
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> delete(@PathVariable int id) {
        return authorService.delete(id);
    }

}
