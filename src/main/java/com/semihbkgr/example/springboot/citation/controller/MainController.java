package com.semihbkgr.example.springboot.citation.controller;

import com.semihbkgr.example.springboot.citation.model.User;
import com.semihbkgr.example.springboot.citation.service.UserService;
import com.semihbkgr.example.springboot.citation.validate.UserBlacklistValidator;
import com.semihbkgr.example.springboot.citation.validate.UserConstraintValidator;
import com.semihbkgr.example.springboot.citation.validate.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

import java.util.LinkedList;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserConstraintValidator userConstraintValidator;
    private final UserBlacklistValidator userBlacklistValidator;

    @GetMapping
    public String main() {
        return "main";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public Mono<String> signupProcess(@ModelAttribute User user, Model model) {
        return userConstraintValidator.validate(user, false)
                .then(userBlacklistValidator.validate(user, false))
                .doOnNext(this::encodePassword)
                .flatMap(userService::save)
                .thenReturn("redirect:index")
                .onErrorResume(ValidationException.class, e -> {
                    model.addAttribute("invalid_field_map", e.getInvalidFieldMap());
                    return Mono.just("signup");
                }).onErrorResume(DataIntegrityViolationException.class, e -> {
                    var errorMessageList = new LinkedList<>();
                    errorMessageList.add(e.getMessage());
                    model.addAttribute("error_message_list", errorMessageList);
                    return Mono.just("signup");
                });
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

}
