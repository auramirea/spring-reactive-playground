package com.pivotal.springboot2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutingConfiguration {

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(TvshowsHandler tvshowsHandler) {
        return route(GET("/tvshows"), tvshowsHandler::getTvShows)
                .andRoute(GET("/tvshowsnames"), tvshowsHandler::getTvShowsNames)
                .andRoute(GET("/tvshows/{id}"), tvshowsHandler::getTvShow);
    }
}
