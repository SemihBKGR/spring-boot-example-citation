package com.semihbkgr.example.springboot.citation.service;

import com.semihbkgr.example.springboot.citation.model.Authority;
import com.semihbkgr.example.springboot.citation.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository repository;

    @Override
    public Mono<Authority> save(Authority authority) {
        return repository.save(authority);
    }

    @Override
    public Flux<Authority> findAll(Pageable pageable) {
        return repository.findAllBy(pageable);
    }

    @Override
    public Mono<Void> delete(int id) {
        return repository.deleteById(id);
    }

}
