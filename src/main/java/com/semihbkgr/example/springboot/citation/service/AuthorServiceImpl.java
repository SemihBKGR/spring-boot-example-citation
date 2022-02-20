package com.semihbkgr.example.springboot.citation.service;

import com.semihbkgr.example.springboot.citation.model.Author;
import com.semihbkgr.example.springboot.citation.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Author> save(Author author) {
        return repository.save(author);
    }

    @Override
    public Mono<Author> find(int id) {
        return repository.findById(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Void> delete(int id) {
        return repository.deleteById(id);
    }

}
