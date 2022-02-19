package com.semihbkgr.example.springboot.citation.controller;


import com.semihbkgr.example.springboot.citation.model.Authority;
import com.semihbkgr.example.springboot.citation.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/authority")
@RequiredArgsConstructor
public class AuthorityController {

    private static final int AUTHORITY_MAX_SIZE = 100;
    private static final int AUTHORITY_DEFAULT_SIZE = 10;
    private static final String AUTHORITY_DEFAULT_SIZE_STRING = "10";

    private static final String AUTHORITY_SORT_COLUMN = "created_at";

    private final AuthorityService authorityService;

    @GetMapping
    public Flux<Authority> get(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                               @RequestParam(value = "size", required = false, defaultValue = AUTHORITY_DEFAULT_SIZE_STRING) int size) {
        size = size <= 0 ? AUTHORITY_DEFAULT_SIZE : Math.min(size, AUTHORITY_MAX_SIZE);
        return authorityService.findAll(PageRequest.of(page, size).withSort(Sort.Direction.ASC, AUTHORITY_SORT_COLUMN));
    }

    @PostMapping
    public Mono<Authority> save(@RequestBody Authority authority) {
        return authorityService.save(authority);
    }

    @PutMapping("/{id}")
    public Mono<Authority> update(@PathVariable int id, @RequestBody Authority authority) {
        authority.setId(id);
        return authorityService.save(authority);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable int id) {
        return authorityService.delete(id);
    }

}
