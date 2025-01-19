package com.connections.service;

import com.connections.external.musicbrainz.dto.MusicBrainzArtist;
import com.connections.external.musicbrainz.dto.MusicBrainzSearchResult;
import reactor.core.publisher.Mono;

/**
 * Defines the contract for retrieving artist information from an external music service (MusicBrainz).
 * Provides methods to search for artists by name and fetch detailed artist information by MBID.
 */
public interface IInspirationService {

    /**
     * Searches for artists matching the specified name or partial name.
     *
     * @param name the name (or partial name) of the artist(s) to search for
     * @return a Mono emitting a MusicBrainzSearchResult containing the list of matching artists
     */
    Mono<MusicBrainzSearchResult> searchForArtist(String name);

    /**
     * Retrieves detailed information about an artist by their MusicBrainz ID (MBID).
     *
     * @param mbid the unique MBID of the artist
     * @return a Mono emitting the MusicBrainzArtist, or empty if no matching artist is found
     */
    Mono<MusicBrainzArtist> findArtistById(String mbid);
}