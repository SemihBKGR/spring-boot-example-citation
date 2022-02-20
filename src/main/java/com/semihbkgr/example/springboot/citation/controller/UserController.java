package com.semihbkgr.example.springboot.citation.controller;

import com.semihbkgr.example.springboot.citation.config.SecurityConfig;
import com.semihbkgr.example.springboot.citation.model.User;
import com.semihbkgr.example.springboot.citation.service.UserService;
import com.semihbkgr.example.springboot.citation.validate.UserBlacklistValidator;
import com.semihbkgr.example.springboot.citation.validate.UserConstraintValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserConstraintValidator userConstraintValidator;
    private final UserBlacklistValidator userBlacklistValidator;

    @GetMapping
    public Mono<User> get() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(SecurityConfig.SecurityUser.class::cast)
                .flatMap(securityUser -> userService.find(securityUser.getId()));
    }

    @GetMapping("/{id}")
    public Mono<User> get(@PathVariable int id) {
        return userService.find(id);
    }

    @GetMapping("/username/{username}")
    public Mono<User> getByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @PostMapping
    public Mono<User> signup(@RequestBody User user) {
        return userConstraintValidator.validate(user, false)
                .then(userBlacklistValidator.validate(user, false))
                .doOnNext(this::encodePassword)
                .flatMap(userService::save);
    }

    @PutMapping
    public Mono<User> update(@RequestBody User user) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(SecurityConfig.SecurityUser.class::cast)
                .flatMap(securityUser -> {
                    user.setId(securityUser.getId());
                    return userService.save(user);
                });
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

}
