package com.connections.service;

import com.connections.dto.EventDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Defines the contract for operations related to "events."
 * Provides methods to create, retrieve, update, and delete event records.
 */
public interface IEventService {

    /**
     * Retrieves all events in the system.
     *
     * @return a Flux stream of EventDTO objects
     */
    Flux<EventDTO> findAll();

    /**
     * Finds a specific event by its unique identifier.
     *
     * @param eventId the ID of the event to retrieve
     * @return a Mono emitting the found EventDTO, or empty if not found
     */
    Mono<EventDTO> findById(Long eventId);

    /**
     * Saves (creates or updates) an event in the system.
     *
     * @param eventDto an EventDTO containing the event's data
     * @return a Mono emitting the saved EventDTO
     */
    Mono<EventDTO> save(EventDTO eventDto);

    /**
     * Deletes an event by its unique identifier.
     *
     * @param eventId the ID of the event to remove
     * @return a Mono signaling completion of the deletion operation
     */
    Mono<Void> delete(Long eventId);
}