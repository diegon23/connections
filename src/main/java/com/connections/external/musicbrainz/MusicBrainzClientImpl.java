package com.connections.external.musicbrainz;

import com.connections.external.musicbrainz.dto.MusicBrainzArtist;
import com.connections.external.musicbrainz.dto.MusicBrainzSearchResult;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class MusicBrainzClientImpl implements MusicBrainzClient {

    private final WebClient webClient;

    public MusicBrainzClientImpl(@Qualifier("musicBrainzWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<MusicBrainzSearchResult> searchArtists(String artistName) {
        // GET /ws/2/artist?query=artist:{artistName}&fmt=json
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/artist/")
                        .queryParam("query", "artist:" + artistName)
                        .queryParam("fmt", "json")
                        .build())
                .retrieve()
                .bodyToMono(MusicBrainzSearchResult.class);
    }

    @Override
    public Mono<MusicBrainzArtist> getArtistById(String mbid) {
        // GET /ws/2/artist/{mbid}?fmt=json
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/artist/{mbid}")
                        .queryParam("fmt", "json")
                        .build(mbid))
                .retrieve()
                .bodyToMono(MusicBrainzArtist.class);
    }
}