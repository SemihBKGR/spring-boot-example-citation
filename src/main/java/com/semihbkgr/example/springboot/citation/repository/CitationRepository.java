package com.semihbkgr.example.springboot.citation.repository;

import com.semihbkgr.example.springboot.citation.model.Citation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CitationRepository extends R2dbcRepository<Citation, Integer> {

    Flux<Citation> findAllByUser(int user, Pageable pageable);

    Flux<Citation> findAllByBook(int book, Pageable pageable);

    Mono<Void> deleteByIdAndUser(int id, int user);

}
