package com.semihbkgr.example.springboot.citation.validate;

import reactor.core.publisher.Mono;

public interface Validator<E> {

    Mono<E> validate(E e, boolean lenient);

}
