package com.semihbkgr.example.springboot.citation.controller;

import com.semihbkgr.example.springboot.citation.model.Author;
import com.semihbkgr.example.springboot.citation.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/{id}")
    public Mono<Author> get(@PathVariable int id) {
        return authorService.findById(id);
    }

    @PostMapping
    public Mono<Author> save(@RequestBody Author author) {
        return authorService.save(author);
    }

}
