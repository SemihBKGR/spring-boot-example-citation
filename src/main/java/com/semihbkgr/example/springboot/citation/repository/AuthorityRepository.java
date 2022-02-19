package com.semihbkgr.example.springboot.citation.repository;

import com.semihbkgr.example.springboot.citation.model.Authority;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface AuthorityRepository extends R2dbcRepository<Authority, Integer> {

    Flux<Authority> findAllBy(Pageable pageable);

}
