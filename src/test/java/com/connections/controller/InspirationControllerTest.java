package com.connections.controller;

import com.connections.external.musicbrainz.dto.MusicBrainzArtist;
import com.connections.external.musicbrainz.dto.MusicBrainzSearchResult;
import com.connections.service.IInspirationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Tests for InspirationController using WebFluxTest + MockBean for the service.
 */
@WebFluxTest(InspirationController.class)
class InspirationControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private IInspirationService inspirationService;

    private MusicBrainzSearchResult dummySearchResult;
    private MusicBrainzArtist dummyArtist;

    @BeforeEach
    void setup() {
        // Setup dummy data
        dummySearchResult = new MusicBrainzSearchResult();
        dummySearchResult.setCount(5);

        dummyArtist = new MusicBrainzArtist();
        dummyArtist.setName("Radiohead");
    }

    @Test
    void testFindByName() throws Exception {
        // Given
        given(inspirationService.searchForArtist(anyString()))
                .willReturn(Mono.just(dummySearchResult));

        // When + Then
        webTestClient.get()
                .uri("/inspiration/name/Radiohead")
                .exchange()
                .expectStatus().isOk()
                .expectBody(MusicBrainzSearchResult.class)
                .isEqualTo(dummySearchResult);

        // Verify
        verify(inspirationService).searchForArtist("Radiohead");
    }

    @Test
    void testFindById() throws Exception {
        // Given
        given(inspirationService.findArtistById(anyString()))
                .willReturn(Mono.just(dummyArtist));

        // When + Then
        webTestClient.get()
                .uri("/inspiration/id/a74b1b7f-71a5-4011-9441-d0b5e4122711")
                .exchange()
                .expectStatus().isOk()
                .expectBody(MusicBrainzArtist.class)
                .isEqualTo(dummyArtist);

        // Verify
        verify(inspirationService).findArtistById("a74b1b7f-71a5-4011-9441-d0b5e4122711");
    }
}