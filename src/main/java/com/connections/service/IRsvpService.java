package com.connections.service;


import com.connections.dto.RsvpDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Defines the contract for RSVP-related operations,
 * such as creating, retrieving, updating, and deleting
 * RSVP records in the system.
 */
public interface IRsvpService {

    /**
     * Retrieves all RSVPs in the system.
     *
     * @return a Flux of RsvpDTO objects representing all RSVPs
     */
    Flux<RsvpDTO> findAll();

    /**
     * Finds a specific RSVP by its unique identifier.
     *
     * @param rsvpId the ID of the RSVP to retrieve
     * @return a Mono emitting the found RsvpDTO, or empty if not found
     */
    Mono<RsvpDTO> findById(Long rsvpId);

    /**
     * Saves (creates or updates) an RSVP in the system.
     *
     * @param rsvp the RsvpDTO containing the RSVP details
     * @return a Mono emitting the saved RsvpDTO
     */
    Mono<RsvpDTO> save(RsvpDTO rsvp);

    /**
     * Deletes an RSVP by its unique identifier.
     *
     * @param rsvpId the ID of the RSVP to remove
     * @return a Mono signaling completion of the deletion operation
     */
    Mono<Void> delete(Long rsvpId);
}