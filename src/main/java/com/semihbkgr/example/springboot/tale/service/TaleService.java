package com.semihbkgr.example.springboot.tale.service;

import com.semihbkgr.example.springboot.tale.model.Tale;
import reactor.core.publisher.Mono;

public interface TaleService {

    Mono<Tale> save(Tale tale);

}
