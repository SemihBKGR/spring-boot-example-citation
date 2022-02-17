package com.semihbkgr.example.springboot.tale.service;

import com.semihbkgr.example.springboot.tale.model.User;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> save(User user);

    Mono<User> findById(int id);

    Mono<User> findByUsername(String username);

}
