package com.connections.controller;

import com.connections.external.musicbrainz.dto.MusicBrainzArtist;
import com.connections.external.musicbrainz.dto.MusicBrainzSearchResult;
import com.connections.service.IInspirationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Controller responsible for "inspiration" endpoints, which integrate with
 * external music data from MusicBrainz to find artists by name or ID.
 */

@RestController
@RequestMapping("/inspiration")
@Tag(name = "Inspiration Controller", description = "Endpoints for searching artists in the MusicBrainz service.")
public class InspirationController {

    final IInspirationService service;

    public InspirationController(final IInspirationService service) {
        this.service = service;
    }

    /**
     * Searches for artists by name using the external MusicBrainz API.
     *
     * @param name The artist's name or partial name.
     * @return A reactive Mono containing a MusicBrainzSearchResult with matching artists.
     * @throws Exception If an error occurs during the external API call.
     */
    @Operation(
            summary = "Find artists by name",
            description = "Retrieves a list of artists from MusicBrainz matching the specified name."
    )
    @GetMapping("/name/{name}")
    public Mono<MusicBrainzSearchResult> findByName(@PathVariable("name") final String name) throws Exception {
        return service.searchForArtist(name);
    }

    /**
     * Retrieves artist details by their MusicBrainz ID (MBID).
     *
     * @param id The MBID of the artist to retrieve.
     * @return A reactive Mono containing the MusicBrainzArtist object if found.
     * @throws Exception If an error occurs during the external API call.
     */
    @Operation(
            summary = "Find an artist by MBID",
            description = "Uses the artist's MBID to fetch detailed information from MusicBrainz."
    )
    @GetMapping("/id/{id}")
    public Mono<MusicBrainzArtist> findById(@PathVariable("id") final String id) throws Exception {
        return service.findArtistById(id);
    }

}