package com.pivotal.springboot2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class TvshowsHandler {
    @Autowired
    private ReactiveTvshowRepository tvshowRepository;

    public Mono<ServerResponse> getTvShows(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(tvshowRepository.findAll(), TvShow.class);
    }

    public Mono<ServerResponse> getTvShowsNames(ServerRequest serverRequest) {
        Mono<List<String>> tvshows =  tvshowRepository.findAll().map(TvShow::getName).collectList();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(tvshows, new ParameterizedTypeReference<List<String>>() {});
    }

    public Mono<ServerResponse> getTvShow(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(tvshowRepository.findById(id), TvShow.class);
    }

    public Mono<ServerResponse> createTvShow(ServerRequest serverRequest) {
        Mono<TvShow> tvShowMono = serverRequest.bodyToMono(TvShow.class)
                .flatMap(tvshowRepository::save);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(tvShowMono, TvShow.class);
    }
}
