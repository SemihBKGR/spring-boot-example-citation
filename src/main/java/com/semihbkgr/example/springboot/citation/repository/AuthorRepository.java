package com.semihbkgr.example.springboot.citation.repository;

import com.semihbkgr.example.springboot.citation.model.Author;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface AuthorRepository extends R2dbcRepository<Author, Integer> {
}
