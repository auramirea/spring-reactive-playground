package com.pivotal.springboot2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class Springboot2ApplicationTests {

	@Autowired
	private WebTestClient client;

	@MockBean
	private ReactiveTvshowRepository tvshowRepository;

	private Iterable<TvShow> tvShows = Arrays.asList(
			new TvShow.TvShowBuilder().name("Gilmore Girls").build(),
			new TvShow.TvShowBuilder().name("Seinfeld").build());

	@Before
	public void init() {
		given(tvshowRepository.findAll()).willReturn(Flux.fromIterable(tvShows));
		given(tvshowRepository.findById(anyString())).willReturn(Mono.just(tvShows.iterator().next()));
		given(tvshowRepository.save(any())).willReturn(Mono.just(tvShows.iterator().next()));
	}

	@Test
	public void shouldReturnTvshowsNonEmptyList () {
		client.get().uri("/tvshows")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBodyList(TvShow.class)
				.hasSize(2)
				.consumeWith(result ->
					assertThat(result.getResponseBody().stream().map(TvShow::getName).map(String::toLowerCase)
							.collect(toList()).containsAll(Arrays.asList("gilmore girls", "seinfeld"))).isTrue());

	}

	@Test
	public void shouldReturnTvshowsnamesNonEmptyList () {
		client.get().uri("/tvshowsnames")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.json("[\"Gilmore Girls\",\"Seinfeld\"]");
	}

	@Test
	public void shouldReturnTvshowById () {
		client.get().uri("/tvshows/1")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody(TvShow.class)
				.consumeWith(result ->
						assertThat(result.getResponseBody().getName().equalsIgnoreCase("gilmore girls")).isTrue());

	}

}
