package com.connections.external.musicbrainz;

import com.connections.external.musicbrainz.dto.MusicBrainzArtist;
import com.connections.external.musicbrainz.dto.MusicBrainzSearchResult;
import reactor.core.publisher.Mono;

public interface MusicBrainzClient {

    /**
     * Searches for artists by name.
     * E.g. GET /ws/2/artist?query=artist:{artistName}&fmt=json
     *
     * @param artistName The name or partial name of the artist to search for.
     * @return A Mono emitting a MusicBrainzSearchResult containing a list of matching artists.
     */
    Mono<MusicBrainzSearchResult> searchArtists(String artistName);

    /**
     * Retrieves an artist by their MBID (MusicBrainz ID).
     * E.g. GET /ws/2/artist/{mbid}?fmt=json
     *
     * @param mbid The MusicBrainz ID (UUID) of the artist.
     * @return A Mono emitting the MusicBrainzArtist if found.
     */
    Mono<MusicBrainzArtist> getArtistById(String mbid);

}