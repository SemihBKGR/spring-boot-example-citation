package com.semihbkgr.example.springboot.tale.repository;

import com.semihbkgr.example.springboot.tale.model.Tale;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaleRepository extends R2dbcRepository<Tale, Integer> {

    @Query("SELECT * FROM tales INNER JOIN users ON tales.author=users.id WHERE tales.title=:title AND users.username=:username")
    Mono<Tale> findByTitleAndAuthorUsername(String title, String username);

    Flux<Tale> findAllByAuthor(int author);

}
