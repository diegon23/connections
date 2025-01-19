package com.connections.controller;

import com.connections.dto.EventDTO;
import com.connections.service.IEventService;
import com.connections.service.impl.EventServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller responsible for managing Events.
 * Provides endpoints to list, retrieve, create, and delete Event resources.
 */

@RestController
@RequestMapping("/event")
@Tag(name = "Event Controller", description = "Endpoints for managing events.")
public class EventController {

    final IEventService service;

    public EventController(final IEventService service) {
        this.service = service;
    }

    /**
     * Retrieves all events in the system.
     *
     * @return A Flux stream of EventDTO objects.
     * @throws Exception if an error occurs during retrieval.
     */
    @Operation(
            summary = "Retrieve all events",
            description = "Returns a reactive Flux of EventDTO containing all events."
    )
    @GetMapping("/all")
    public Flux<EventDTO> findAll() throws Exception {
        return service.findAll();
    }

    /**
     * Finds a specific event by its identifier.
     *
     * @param id The ID of the event to retrieve.
     * @return A Mono emitting the found EventDTO, or empty if not found.
     * @throws Exception if an error occurs during retrieval.
     */
    @Operation(
            summary = "Find an event by ID",
            description = "Looks up an event by its ID and returns the matching EventDTO, if any."
    )
    @GetMapping("/id/{id}")
    public Mono<EventDTO> findById(@PathVariable("id") final Long id) throws Exception {
        return service.findById(id);
    }

    /**
     * Saves (creates or updates) an Event resource.
     *
     * @param dto The EventDTO payload containing event details to be saved.
     * @return A Mono emitting the saved EventDTO.
     */
    @Operation(
            summary = "Save an event",
            description = "Creates or updates an event using the provided EventDTO."
    )
    @PostMapping
    public Mono<EventDTO> save(@RequestBody final EventDTO dto){
        return service.save(dto);
    }

    /**
     * Deletes an event identified by its ID.
     *
     * @param id The ID of the event to delete.
     * @return A Mono emitting an HTTP 200 (OK) if deletion was successful.
     */
    @Operation(
            summary = "Delete an event by ID",
            description = "Removes an event from the system using its ID."
    )
    @DeleteMapping("/id/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") final Long id){
        return this.service.delete(id)
                .map(ResponseEntity::ok);
    }

}