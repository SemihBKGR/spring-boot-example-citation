package com.semihbkgr.example.springboot.citation.service;

import com.semihbkgr.example.springboot.citation.model.Book;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

    Mono<Book> save(Book book);

    Mono<Book> find(int id);

    Flux<Book> findAllByAuthor(int author, Pageable pageable);

    Mono<Void> delete(int id);

}
