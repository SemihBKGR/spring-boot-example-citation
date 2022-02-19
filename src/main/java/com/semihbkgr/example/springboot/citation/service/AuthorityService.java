package com.semihbkgr.example.springboot.citation.service;

import com.semihbkgr.example.springboot.citation.model.Authority;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorityService {

    Mono<Authority> save(Authority authority);

    Flux<Authority> findAll(Pageable pageable);

    Mono<Void> delete(int id);

}
