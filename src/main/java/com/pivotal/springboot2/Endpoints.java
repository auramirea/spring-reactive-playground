package com.pivotal.springboot2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@RestController
public class Endpoints {
    @GetMapping("/shows")
    public Mono<List<String>> getShows() {
        return Mono.just(Arrays.asList("tvshow1", "tvshow2"));
    }
    @GetMapping("/shows_nonreactive")
    public List<String> getShows_nonReactive() {
        return Arrays.asList("tvshow1", "tvshow2");
    }
}
