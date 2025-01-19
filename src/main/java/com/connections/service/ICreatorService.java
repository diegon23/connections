package com.connections.service;

import com.connections.dto.CreatorDTO;
import com.connections.dto.InspirationDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Defines the contract for operations related to "creators" (artists).
 * Provides methods to create, retrieve, update, and delete creator records.
 */
public interface ICreatorService {

    /**
     * Retrieves all creators in the system.
     *
     * @return a Flux containing all CreatorDTO objects
     */
    Flux<CreatorDTO> findAll();

    /**
     * Finds a specific creator by its unique identifier.
     *
     * @param creatorId the ID of the creator to retrieve
     * @return a Mono emitting the found CreatorDTO, or empty if not found
     */
    Mono<CreatorDTO> findById(Long creatorId);

    /**
     * Find an artist by id and add it as inspiration to a Creator
     *
     * @param creatorId     the ID of the creator to add the inspiration
     * @param inspirationId the ID of the inspiration to add to the Creator
     * @return a Mono emitting CreatorDTO
     */
    Mono<InspirationDTO> addInspirationToCreator(Long creatorId, String inspirationId);

    /**
     * Saves (creates or updates) a creator in the system.
     *
     * @param creator a CreatorDTO containing the creator's data
     * @return a Mono emitting the saved CreatorDTO
     */
    Mono<CreatorDTO> save(CreatorDTO creator);

    /**
     * Deletes a creator by its unique identifier.
     *
     * @param creatorId the ID of the creator to remove
     * @return a Mono signaling completion of the deletion operation
     */
    Mono<Void> delete(Long creatorId);
}