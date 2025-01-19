package com.connections.service.impl;

import com.connections.external.musicbrainz.MusicBrainzClient;
import com.connections.external.musicbrainz.dto.MusicBrainzArtist;
import com.connections.external.musicbrainz.dto.MusicBrainzSearchResult;
import com.connections.service.IInspirationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of {@link IInspirationService}.
 * <p>For method details, see the interface documentation.</p>
 */
@Service
@RequiredArgsConstructor
public class InspirationServiceImpl implements IInspirationService {

    private final MusicBrainzClient musicBrainzClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<MusicBrainzSearchResult> searchForArtist(String name) {
        return musicBrainzClient.searchArtists(name)
                .doOnNext(result -> System.out.println("Found " + result.getCount() + " artists."));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<MusicBrainzArtist> findArtistById(String mbid) {
        return musicBrainzClient.getArtistById(mbid)
                .doOnNext(artist -> System.out.println("Artist name: " + artist.getName()));
    }
}
