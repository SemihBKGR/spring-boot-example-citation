package com.semihbkgr.example.springboot.citation.service;

import com.semihbkgr.example.springboot.citation.model.Author;
import com.semihbkgr.example.springboot.citation.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Mono<Author> save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Mono<Author> update(int id, Author author) {
        return authorRepository.findById(id)
                .flatMap(authorFromDB -> {
                    authorFromDB.setName(author.getName());
                    authorFromDB.setSurname(author.getSurname());
                    authorFromDB.setBiography(author.getBiography());
                    authorFromDB.setBirthYear(author.getBirthYear());
                    authorFromDB.setDeathYear(author.getDeathYear());
                    return authorRepository.save(authorFromDB);
                });
    }

    @Override
    public Mono<Author> findById(int id) {
        return authorRepository.findById(id);
    }

}
