package com.semihbkgr.example.springboot.citation.service;

import com.semihbkgr.example.springboot.citation.model.Author;
import reactor.core.publisher.Mono;

public interface AuthorService {

    Mono<Author> save(Author author);

    Mono<Author> find(int id);

    Mono<Void> delete(int id);

}
