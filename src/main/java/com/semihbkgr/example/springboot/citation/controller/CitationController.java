package com.semihbkgr.example.springboot.citation.controller;

import com.semihbkgr.example.springboot.citation.config.SecurityConfig;
import com.semihbkgr.example.springboot.citation.model.Citation;
import com.semihbkgr.example.springboot.citation.service.CitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/citation")
@RequiredArgsConstructor
public class CitationController {

    private static final int CITATION_MAX_SIZE = 100;
    private static final int CITATION_DEFAULT_SIZE = 10;
    private static final String CITATION_DEFAULT_SIZE_STRING = "10";

    private static final String CITATION_SORT_COLUMN = "createdAt";

    private final CitationService citationService;

    @GetMapping("/{id}")
    public Mono<Citation> get(@PathVariable int id) {
        return citationService.find(id);
    }

    @GetMapping("/user/{user}")
    public Flux<Citation> getAllByUser(@PathVariable int user,
                                       @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                       @RequestParam(value = "size", required = false, defaultValue = CITATION_DEFAULT_SIZE_STRING) int size) {
        size = size <= 0 ? CITATION_DEFAULT_SIZE : Math.min(size, CITATION_MAX_SIZE);
        return citationService.findAllByUser(user, PageRequest.of(page, size).withSort(Sort.Direction.DESC, CITATION_SORT_COLUMN));
    }

    @GetMapping("/book/{book}")
    public Flux<Citation> getAllByBook(@PathVariable int book,
                                       @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                       @RequestParam(value = "size", required = false, defaultValue = CITATION_DEFAULT_SIZE_STRING) int size) {
        size = size <= 0 ? CITATION_DEFAULT_SIZE : Math.min(size, CITATION_MAX_SIZE);
        return citationService.findAllByBook(book, PageRequest.of(page, size).withSort(Sort.Direction.DESC, CITATION_SORT_COLUMN));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Citation> save(@RequestBody Citation citation) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(SecurityConfig.SecurityUser.class::cast)
                .flatMap(securityUser -> {
                    citation.setUser(securityUser.getId());
                    return citationService.save(citation);
                });
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Citation> update(@PathVariable int id, @RequestBody Citation citation) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(SecurityConfig.SecurityUser.class::cast)
                .flatMap(securityUser -> {
                    citation.setUser(securityUser.getId());
                    citation.setId(id);
                    return citationService.save(citation);
                });
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> delete(@PathVariable int id,
                             @RequestParam(name = "force", required = false, defaultValue = "false") boolean force) {
        if (force)
            return citationService.delete(id);
        else
            return ReactiveSecurityContextHolder.getContext()
                    .map(SecurityContext::getAuthentication)
                    .filter(Authentication::isAuthenticated)
                    .map(Authentication::getPrincipal)
                    .map(SecurityConfig.SecurityUser.class::cast)
                    .flatMap(securityUser -> citationService.delete(id, securityUser.getId()));
    }

}
