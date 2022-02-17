package com.semihbkgr.example.springboot.tale.repository;

import com.semihbkgr.example.springboot.tale.model.Tale;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface TaleRepository extends R2dbcRepository<Tale,Integer> {

}
