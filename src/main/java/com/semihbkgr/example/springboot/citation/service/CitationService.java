package com.semihbkgr.example.springboot.citation.service;

import com.semihbkgr.example.springboot.citation.model.Citation;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CitationService {

    Mono<Citation> save(Citation citation);

    Mono<Citation> find(int id);

    Flux<Citation> findAllByUser(int user, Pageable pageable);

    Flux<Citation> findAllByBook(int book, Pageable pageable);

    Mono<Void> delete(int id);

    Mono<Void> delete(int id, int user);

}
