package com.semihbkgr.example.springboot.citation.service;

import com.semihbkgr.example.springboot.citation.model.User;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> save(User user);

    Mono<User> find(int id);

    Mono<User> findByUsername(String username);

}
