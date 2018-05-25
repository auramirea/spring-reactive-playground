package com.pivotal.springboot2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class Springboot2ApplicationTests {

	@Autowired
	private WebTestClient client;

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
						assertThat(result.getResponseBody().stream().map(TvShow::getName).collect(toList())
								.containsAll(Arrays.asList("tvshow1", "tvshow2"))));

	}

	@Test
	public void shouldReturnTvshowsnamesNonEmptyList () {
		client.get().uri("/tvshowsnames")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.json("[\"tvshow1\",\"tvshow2\"]");
	}

	@Test
	public void shouldReturnTvshowById () {
		client.get().uri("/tvshows/1")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody(String.class)
				.consumeWith(result ->
						assertThat(result.getResponseBody().equalsIgnoreCase("tvshow1")));

	}

}
