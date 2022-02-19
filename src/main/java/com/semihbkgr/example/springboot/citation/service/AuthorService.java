package com.semihbkgr.example.springboot.citation.service;

import com.semihbkgr.example.springboot.citation.model.Author;
import reactor.core.publisher.Mono;

public interface AuthorService {

    Mono<Author> save(Author author);

    Mono<Author> update(int id, Author author);

    Mono<Author> find(int id);

}
