package com.semihbkgr.example.springboot.citation.repository;

import com.semihbkgr.example.springboot.citation.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface BookRepository extends R2dbcRepository<Book, Integer> {

    Flux<Book> findByAuthor(int author, Pageable pageable);

}
