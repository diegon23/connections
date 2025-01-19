package com.connections.service;

import com.connections.dto.VenueDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Defines the contract for venue-related operations,
 * such as creating, retrieving, updating, and deleting
 * venue records in the system.
 */
public interface IVenueService {

    /**
     * Retrieves all venues in the system.
     *
     * @return a Flux of VenueDTO objects representing all venues
     */
    Flux<VenueDTO> findAll();

    /**
     * Finds a specific venue by its unique identifier.
     *
     * @param venueId the ID of the venue to retrieve
     * @return a Mono emitting the found VenueDTO, or empty if not found
     */
    Mono<VenueDTO> findById(Long venueId);

    /**
     * Saves (creates or updates) a venue in the system.
     *
     * @param venue the VenueDTO containing the venue details
     * @return a Mono emitting the saved VenueDTO
     */
    Mono<VenueDTO> save(VenueDTO venue);

    /**
     * Deletes a venue by its unique identifier.
     *
     * @param venueId the ID of the venue to remove
     * @return a Mono signaling completion of the deletion operation
     */
    Mono<Void> delete(Long venueId);
}