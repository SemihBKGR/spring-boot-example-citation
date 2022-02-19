package com.semihbkgr.example.springboot.citation.service;

import com.semihbkgr.example.springboot.citation.model.Citation;
import com.semihbkgr.example.springboot.citation.repository.CitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CitationServiceImpl implements CitationService {

    private final CitationRepository repository;

    @Override
    public Mono<Citation> save(Citation citation) {
        return repository.save(citation);
    }

    @Override
    public Mono<Citation> find(int id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Citation> findAllByUser(int user, Pageable pageable) {
        return repository.findAllByUser(user, pageable);
    }

    @Override
    public Flux<Citation> findAllByBook(int book, Pageable pageable) {
        return repository.findAllByBook(book, pageable);
    }

    @Override
    public Mono<Void> delete(int id) {
        return repository.deleteById(id);
    }

}
