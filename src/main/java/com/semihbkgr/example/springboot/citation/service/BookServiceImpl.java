package com.semihbkgr.example.springboot.citation.service;

import com.semihbkgr.example.springboot.citation.model.Book;
import com.semihbkgr.example.springboot.citation.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @Override
    public Mono<Book> save(Book book) {
        return repository.save(book);
    }

    @Override
    public Mono<Book> find(int id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Book> findAllByAuthor(int author, Pageable pageable) {
        return repository.findByAuthor(author,pageable);
    }

    @Override
    public Mono<Void> delete(int id) {
        return repository.deleteById(id);
    }

}
