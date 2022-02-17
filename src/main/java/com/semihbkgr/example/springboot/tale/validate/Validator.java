package com.semihbkgr.example.springboot.tale.validate;

import reactor.core.publisher.Mono;

public interface Validator<E> {

    Mono<E> validate(E e);

}
