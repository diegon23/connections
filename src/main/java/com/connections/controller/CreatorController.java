package com.connections.controller;

import com.connections.dto.CreatorDTO;
import com.connections.dto.InspirationDTO;
import com.connections.service.ICreatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller responsible for managing Creators (artists).
 * Provides endpoints to list, retrieve, create, and delete Creators.
 */

@RestController
@RequestMapping("/creator")
@Tag(name = "Creator Controller", description = "Endpoints for managing creators/artists.")
public class CreatorController {

    final ICreatorService service;

    public CreatorController(final ICreatorService service) {
        this.service = service;
    }

    /**
     * Retrieves all creators in the system.
     *
     * @return A Flux stream of CreatorDTO objects.
     * @throws Exception if an error occurs during retrieval.
     */
    @Operation(
            summary = "Retrieve all creators",
            description = "Returns a reactive Flux of CreatorDTO containing all creators/artists."
    )
    @GetMapping("/all")
    public Flux<CreatorDTO> findAll() throws Exception {
        return service.findAll();
    }

    /**
     * Finds a specific creator by its identifier.
     *
     * @param id The ID of the creator to retrieve.
     * @return A Mono emitting the found CreatorDTO, or empty if not found.
     * @throws Exception if an error occurs during retrieval.
     */
    @Operation(
            summary = "Find a creator by ID",
            description = "Looks up a creator by its ID and returns the matching CreatorDTO, if any."
    )
    @GetMapping("/id/{id}")
    public Mono<CreatorDTO> findById(@PathVariable("id") final Long id) throws Exception {
        return service.findById(id);
    }

    /**
     * Saves (creates or updates) a Creator resource.
     *
     * @param dto The CreatorDTO payload containing creator details to be saved.
     * @return A Mono emitting the saved CreatorDTO.
     */
    @Operation(
            summary = "Save a creator",
            description = "Creates or updates a creator using the provided CreatorDTO."
    )
    @PostMapping
    public Mono<CreatorDTO> save(@RequestBody final CreatorDTO dto){
        return service.save(dto);
    }

    /**
     * Add Inspiration to Creator.
     *
     * @param creatorId The ID of the Creator to add the Inspiration
     * @param inspirationId The ID of the Inspiration to be added to the Creator
     * @return A Mono emitting the updated CreatorDTO.
     */
    @Operation(
            summary = "Add Inspiration to Creator",
            description = "Finds a Creator and add Artist as Inspiration"
    )
    @PostMapping("add-inspiration")
    public Mono<InspirationDTO> save(@RequestParam("creatorId") final Long creatorId,
                                     @RequestParam("inspirationId") final String inspirationId){
        return service.addInspirationToCreator(creatorId, inspirationId);
    }

    /**
     * Deletes a creator identified by its ID.
     *
     * @param id The ID of the creator to delete.
     * @return A Mono emitting an HTTP 200 (OK) if deletion was successful.
     */
    @Operation(
            summary = "Delete a creator by ID",
            description = "Removes a creator from the system using its ID."
    )
    @DeleteMapping("/id/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") final Long id){
        return this.service.delete(id)
                .map(ResponseEntity::ok);
    }

}