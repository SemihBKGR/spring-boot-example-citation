package com.semihbkgr.example.springboot.tale.controller;

import com.semihbkgr.example.springboot.tale.config.SecurityConfig;
import com.semihbkgr.example.springboot.tale.model.Tale;
import com.semihbkgr.example.springboot.tale.model.User;
import com.semihbkgr.example.springboot.tale.service.TaleService;
import com.semihbkgr.example.springboot.tale.service.UserService;
import com.semihbkgr.example.springboot.tale.validate.UserBlacklistValidator;
import com.semihbkgr.example.springboot.tale.validate.UserConstraintValidator;
import com.semihbkgr.example.springboot.tale.validate.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

import java.util.LinkedList;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final TaleService taleService;
    private final PasswordEncoder passwordEncoder;
    private final UserConstraintValidator userConstraintValidator;
    private final UserBlacklistValidator userBlacklistValidator;

    @GetMapping
    public String main() {
        return "main";
    }

    @GetMapping("/{username}")
    public Mono<String> profile(@PathVariable String username, Authentication authentication, Model model) {
        var securityUser = (SecurityConfig.SecurityUser) authentication.getPrincipal();
        return taleService.findAllByAuthor(securityUser.getId())
                .collectList()
                .flatMap(tales -> {
                    model.addAttribute("tales", tales);
                    return userService.findById(securityUser.getId());
                }).map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("owner", user.getUsername().equals(username));
                    return "profile";
                });
    }

    @GetMapping("/{username}/{title}")
    public Mono<String> tale(@PathVariable String username, @PathVariable String title, Authentication authentication, Model model) {
        var securityUser = (SecurityConfig.SecurityUser) authentication.getPrincipal();
        return taleService.findByTitleAndAuthorUsername(title, username)
                .flatMap(tale -> {
                    model.addAttribute("tale", tale);
                    return userService.findById(securityUser.getId());
                }).map(user -> {
                    model.addAttribute("author", user);
                    model.addAttribute("owner", user.getUsername().equals(username));
                    return "tale";
                });
    }

    @GetMapping("/tale")
    public String taleCreate() {
        return "tale-create";
    }

    @PostMapping("/tale")
    public Mono<String> taleCreateProcess(@ModelAttribute Tale tale) {
        return taleService.save(tale)
                .thenReturn("redirect:tale");
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
