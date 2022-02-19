package com.semihbkgr.example.springboot.citation.repository;

import com.semihbkgr.example.springboot.citation.model.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<User, Integer> {

    @Query("SELECT users.*, GROUP_CONCAT(authorities.name) AS authorities FROM users INNER JOIN user_authority_joins ON user_authority_joins.user_id=users.id INNER JOIN authorities ON authorities.id = user_authority_joins.authority_id WHERE users.username=:username")
    Mono<User> findByUsername(String username);

}

