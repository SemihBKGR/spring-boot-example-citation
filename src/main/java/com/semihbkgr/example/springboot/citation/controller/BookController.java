package com.semihbkgr.example.springboot.citation.controller;

import com.semihbkgr.example.springboot.citation.model.Book;
import com.semihbkgr.example.springboot.citation.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private static final int BOOK_MAX_SIZE = 100;
    private static final int BOOK_DEFAULT_SIZE = 10;
    private static final String BOOK_DEFAULT_SIZE_STRING = "10";

    private static final String BOOK_SORT_COLUMN = "createdAt";

    private final BookService bookService;

    @GetMapping("/{id}")
    public Mono<Book> get(@PathVariable int id) {
        return bookService.find(id);
    }

    @GetMapping("/author/{author}")
    public Flux<Book> getByAuthor(@PathVariable int author,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                  @RequestParam(value = "size", required = false, defaultValue = BOOK_DEFAULT_SIZE_STRING) int size) {
        size = size <= 0 ? BOOK_DEFAULT_SIZE : Math.min(size, BOOK_MAX_SIZE);
        return bookService.findAllByAuthor(author, PageRequest.of(page, size).withSort(Sort.Direction.ASC, BOOK_SORT_COLUMN));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Book> save(@RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Book> update(@PathVariable int id, @RequestBody Book book) {
        book.setId(id);
        return bookService.save(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> delete(@PathVariable int id) {
        return bookService.delete(id);
    }

}
