package com.semihbkgr.example.springboot.tale.controller;

import com.semihbkgr.example.springboot.tale.model.Tale;
import com.semihbkgr.example.springboot.tale.service.TaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/tale")
@RequiredArgsConstructor
public class TaleController {

    private final TaleService taleService;

    @GetMapping
    public String tale() {
        return "tale";
    }

    @PostMapping
    public Mono<String> taleSave(@ModelAttribute Tale tale) {
        return taleService.save(tale)
                .thenReturn("redirect:tale");
    }

}
