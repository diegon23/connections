package com.connections.controller;

import com.connections.dto.VenueDTO;
import com.connections.service.IVenueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller responsible for managing Venues (places where events can be hosted).
 * Provides endpoints to list, retrieve, create, and delete Venue resources.
 */

@RestController
@RequestMapping("/venue")
@Tag(name = "Venue Controller", description = "Endpoints for managing venues (locations of events).")
public class VenueController {

    final IVenueService service;

    public VenueController(final IVenueService service) {
        this.service = service;
    }

    /**
     * Retrieves all venues in the system.
     *
     * @return A Flux stream of VenueDTO objects.
     * @throws Exception if an error occurs during retrieval.
     */
    @Operation(
            summary = "Retrieve all venues",
            description = "Returns a reactive Flux of VenueDTO containing all venues in the system."
    )
    @GetMapping("/all")
    public Flux<VenueDTO> findAll() throws Exception {
        return service.findAll();
    }

    /**
     * Finds a specific venue by its identifier.
     *
     * @param id The ID of the venue to retrieve.
     * @return A Mono emitting the found VenueDTO, or empty if not found.
     * @throws Exception if an error occurs during retrieval.
     */
    @Operation(
            summary = "Find a venue by ID",
            description = "Looks up a venue by its ID and returns the matching VenueDTO, if any."
    )
    @GetMapping("/id/{id}")
    public Mono<VenueDTO> findById(@PathVariable("id") final Long id) throws Exception {
        return service.findById(id);
    }

    /**
     * Saves (creates or updates) a Venue resource.
     *
     * @param dto The VenueDTO payload containing venue details to be saved.
     * @return A Mono emitting the saved VenueDTO.
     */
    @Operation(
            summary = "Save a venue",
            description = "Creates or updates a venue using the provided VenueDTO."
    )
    @PostMapping
    public Mono<VenueDTO> save(@RequestBody final VenueDTO dto){
        return service.save(dto);
    }

    /**
     * Deletes a venue identified by its ID.
     *
     * @param id The ID of the venue to delete.
     * @return A Mono emitting an HTTP 200 (OK) if deletion was successful.
     */
    @Operation(
            summary = "Delete a venue by ID",
            description = "Removes a venue from the system using its ID."
    )
    @DeleteMapping("/id/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") final Long id){
        return this.service.delete(id)
                .map(ResponseEntity::ok);
    }

}