package com.connections.controller;

import com.connections.dto.LinkDTO;
import com.connections.service.ILinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller responsible for managing Links associated with creators.
 * Provides endpoints to list, retrieve, create, and delete Link resources.
 */

@RestController
@RequestMapping("/link")
@Tag(name = "Link Controller", description = "Endpoints for managing links referencing creators (e.g., social media).")
public class LinkController {

    final ILinkService service;

    public LinkController(final ILinkService service) {
        this.service = service;
    }

    /**
     * Retrieves all links in the system.
     *
     * @return A Flux stream of LinkDTO objects.
     * @throws Exception if an error occurs during retrieval.
     */
    @Operation(
            summary = "Retrieve all links",
            description = "Returns a reactive Flux of LinkDTO containing all links in the system."
    )
    @GetMapping("/all")
    public Flux<LinkDTO> findAll() throws Exception {
        return service.findAll();
    }

    /**
     * Finds a specific link by its identifier.
     *
     * @param id The ID of the link to retrieve.
     * @return A Mono emitting the found LinkDTO, or empty if not found.
     * @throws Exception if an error occurs during retrieval.
     */
    @Operation(
            summary = "Find a link by ID",
            description = "Looks up a link by its ID and returns the matching LinkDTO, if any."
    )
    @GetMapping("/id/{id}")
    public Mono<LinkDTO> findById(@PathVariable("id") final Long id) throws Exception {
        return service.findById(id);
    }

    /**
     * Saves (creates or updates) a Link resource.
     *
     * @param dto The LinkDTO payload containing link details to be saved.
     * @return A Mono emitting the saved LinkDTO.
     */
    @Operation(
            summary = "Save a link",
            description = "Creates or updates a link using the provided LinkDTO."
    )
    @PostMapping
    public Mono<LinkDTO> save(@RequestBody final LinkDTO dto){
        return service.save(dto);
    }

    /**
     * Deletes a link identified by its ID.
     *
     * @param id The ID of the link to delete.
     * @return A Mono emitting an HTTP 200 (OK) if deletion was successful.
     */
    @Operation(
            summary = "Delete a link by ID",
            description = "Removes a link from the system using its ID."
    )
    @DeleteMapping("/id/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") final Long id){
        return this.service.delete(id)
                .map(ResponseEntity::ok);
    }

}