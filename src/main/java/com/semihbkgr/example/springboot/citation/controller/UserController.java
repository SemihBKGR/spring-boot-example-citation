package com.semihbkgr.example.springboot.citation.controller;

import com.semihbkgr.example.springboot.citation.model.User;
import com.semihbkgr.example.springboot.citation.service.UserService;
import com.semihbkgr.example.springboot.citation.validate.UserBlacklistValidator;
import com.semihbkgr.example.springboot.citation.validate.UserConstraintValidator;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{username}")
    public Mono<User> get(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @PostMapping("/signup")
    public Mono<User> signup(@RequestBody User user) {
        return userConstraintValidator.validate(user, false)
                .then(userBlacklistValidator.validate(user, false))
                .doOnNext(this::encodePassword)
                .flatMap(userService::save);
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

}
