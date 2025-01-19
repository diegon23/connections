package com.connections.controller;

import com.connections.dto.RsvpDTO;
import com.connections.service.IRsvpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller responsible for managing RSVPs (confirmations of attendance at events).
 * Provides endpoints to list, retrieve, create, and delete Rsvp resources.
 */

@RestController
@RequestMapping("/rsvp")
@Tag(name = "Rsvp Controller", description = "Endpoints for managing RSVPs (event confirmations).")
public class RsvpController {

    final IRsvpService service;

    public RsvpController(final IRsvpService service) {
        this.service = service;
    }


    /**
     * Retrieves all RSVPs in the system.
     *
     * @return A Flux stream of RsvpDTO objects.
     * @throws Exception if an error occurs during retrieval.
     */
    @Operation(
            summary = "Retrieve all RSVPs",
            description = "Returns a reactive Flux of RsvpDTO containing all RSVPs."
    )
    @GetMapping("/all")
    public Flux<RsvpDTO> findAll() throws Exception {
        return service.findAll();
    }

    /**
     * Finds a specific RSVP by its identifier.
     *
     * @param id The ID of the RSVP to retrieve.
     * @return A Mono emitting the found RsvpDTO, or empty if not found.
     * @throws Exception if an error occurs during retrieval.
     */
    @Operation(
            summary = "Find an RSVP by ID",
            description = "Looks up an RSVP by its ID and returns the matching RsvpDTO, if any."
    )
    @GetMapping("/id/{id}")
    public Mono<RsvpDTO> findById(@PathVariable("id") final Long id) throws Exception {
        return service.findById(id);
    }

    /**
     * Saves (creates or updates) an RSVP resource.
     *
     * @param dto The RsvpDTO payload containing RSVP details to be saved.
     * @return A Mono emitting the saved RsvpDTO.
     */
    @Operation(
            summary = "Save an RSVP",
            description = "Creates or updates an RSVP using the provided RsvpDTO."
    )
    @PostMapping
    public Mono<RsvpDTO> save(@RequestBody final RsvpDTO dto){
        return service.save(dto);
    }

    /**
     * Deletes an RSVP identified by its ID.
     *
     * @param id The ID of the RSVP to delete.
     * @return A Mono emitting an HTTP 200 (OK) if deletion was successful.
     */
    @Operation(
            summary = "Delete an RSVP by ID",
            description = "Removes an RSVP from the system using its ID."
    )
    @DeleteMapping("/id/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") final Long id){
        return this.service.delete(id)
                .map(ResponseEntity::ok);
    }

}