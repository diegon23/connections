package com.connections.service;

import com.connections.external.musicbrainz.MusicBrainzClient;
import com.connections.external.musicbrainz.dto.MusicBrainzArtist;
import com.connections.external.musicbrainz.dto.MusicBrainzSearchResult;
import com.connections.service.impl.InspirationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Unit test for InspirationServiceImpl, verifying external client calls
 * and transformations.
 */
@ExtendWith(MockitoExtension.class)
class InspirationServiceImplTest {

    @Mock
    private MusicBrainzClient musicBrainzClient;

    @InjectMocks
    private InspirationServiceImpl inspirationServiceImpl;

    private MusicBrainzSearchResult dummySearchResult;
    private MusicBrainzArtist dummyArtist;

    @BeforeEach
    void setup() {
        dummySearchResult = new MusicBrainzSearchResult();
        dummySearchResult.setCount(3);

        dummyArtist = new MusicBrainzArtist();
        dummyArtist.setName("Radiohead");
    }

    @Test
    void testSearchForArtist() {
        // Given
        when(musicBrainzClient.searchArtists(anyString()))
                .thenReturn(Mono.just(dummySearchResult));

        // When
        Mono<MusicBrainzSearchResult> resultMono = inspirationServiceImpl.searchForArtist("Radiohead");

        // Then
        StepVerifier.create(resultMono)
                .expectNext(dummySearchResult)
                .verifyComplete();
    }

    @Test
    void testFindArtistById() {
        // Given
        when(musicBrainzClient.getArtistById(anyString()))
                .thenReturn(Mono.just(dummyArtist));

        // When
        Mono<MusicBrainzArtist> artistMono = inspirationServiceImpl.findArtistById("some-mbid");

        // Then
        StepVerifier.create(artistMono)
                .expectNext(dummyArtist)
                .verifyComplete();
    }
}