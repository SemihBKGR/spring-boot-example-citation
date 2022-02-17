package com.semihbkgr.example.springboot.tale.service;

import com.semihbkgr.example.springboot.tale.model.Tale;
import com.semihbkgr.example.springboot.tale.repository.TaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TaleServiceImpl implements TaleService {

    private final TaleRepository taleRepository;

    @Override
    public Mono<Tale> save(Tale tale) {
        return taleRepository.save(tale);
    }

}
