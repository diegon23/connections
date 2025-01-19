package com.connections.service;

import com.connections.dto.LinkDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Defines the contract for link-related operations.
 * Provides methods to create, retrieve, update, and delete links,
 * typically associated with creators.
 */
public interface ILinkService {

    /**
     * Retrieves all links in the system.
     *
     * @return a Flux of LinkDTO representing all links available
     */
    Flux<LinkDTO> findAll();

    /**
     * Finds a specific link by its unique identifier.
     *
     * @param linkId the ID of the link to retrieve
     * @return a Mono emitting the found LinkDTO, or empty if no match is found
     */
    Mono<LinkDTO> findById(Long linkId);

    /**
     * Saves (creates or updates) a link in the system.
     *
     * @param link the LinkDTO object containing link details
     * @return a Mono emitting the saved LinkDTO
     */
    Mono<LinkDTO> save(LinkDTO link);

    /**
     * Deletes a link by its unique identifier.
     *
     * @param linkId the ID of the link to remove
     * @return a Mono signaling completion of the deletion
     */
    Mono<Void> delete(Long linkId);
}