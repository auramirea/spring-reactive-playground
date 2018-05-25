package com.pivotal.springboot2;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
public class TvshowsHandler {
    public Mono<ServerResponse> getTvShows(ServerRequest serverRequest) {
        Flux<TvShow> tvshows = Flux.fromStream(Stream.of(new TvShow("tvshow1"), new TvShow("tvshow2")));

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(tvshows, TvShow.class);
    }

    public Mono<ServerResponse> getTvShowsNames(ServerRequest serverRequest) {
        Mono<List<String>> tvshows = Mono.just(Arrays.asList("tvshow1", "tvshow2"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(tvshows, new ParameterizedTypeReference<List<String>>() {});
    }

    public Mono<ServerResponse> getTvShow(ServerRequest serverRequest) {
        int id = Integer.parseInt(serverRequest.pathVariable("id"));
        List<String> tvshows = Arrays.asList("tvshow1", "tvshow2");

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(tvshows.get(id-1)), String.class);
    }
}
