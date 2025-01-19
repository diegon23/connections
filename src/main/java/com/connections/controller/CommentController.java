package com.connections.controller;

import com.connections.dto.CommentDTO;
import com.connections.service.ICommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller responsible for managing Comments. Provides endpoints
 * to list, retrieve, create, and delete Comment resources.
 */
@RestController
@RequestMapping("/comment")
@Tag(name = "Comment Controller", description = "Endpoints for managing comments within the application.")
public class CommentController {

    final ICommentService service;

    public CommentController(final ICommentService service) {
        this.service = service;
    }

    /**
     * Retrieves all comments in the system.
     *
     * @return A Flux stream of all CommentDTO objects.
     * @throws Exception if an error occurs during retrieval.
     */
    @Operation(
            summary = "Retrieve all comments",
            description = "Returns a reactive Flux of CommentDTO representing all comments."
    )
    @GetMapping("/all")
    public Flux<CommentDTO> findAll() throws Exception {
        return service.findAll();
    }

    /**
     * Finds a specific comment by its identifier.
     *
     * @param id The ID of the comment to retrieve.
     * @return A Mono emitting the found CommentDTO, or empty if not found.
     * @throws Exception if an error occurs during retrieval.
     */
    @Operation(
            summary = "Find a comment by ID",
            description = "Looks up a comment by its ID and returns the matching CommentDTO, if any."
    )
    @GetMapping("/id/{id}")
    public Mono<CommentDTO> findById(@PathVariable("id") final Long id) throws Exception {
        return service.findById(id);
    }

    /**
     * Saves (creates or updates) a Comment resource.
     *
     * @param dto The CommentDTO payload containing the comment details to save.
     * @return A Mono emitting the saved CommentDTO.
     */
    @Operation(
            summary = "Save a comment",
            description = "Creates or updates a comment using the provided CommentDTO."
    )
    @PostMapping
    public Mono<CommentDTO> save(@RequestBody final CommentDTO dto){
        return service.save(dto);
    }

    /**
     * Deletes a comment identified by its ID.
     *
     * @param id The ID of the comment to delete.
     * @return A Mono emitting an HTTP 200 (OK) if deletion was successful.
     */
    @Operation(
            summary = "Delete a comment by ID",
            description = "Removes a comment from the system using its ID."
    )
    @DeleteMapping("/id/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") final Long id){
        return this.service.delete(id)
                .map(ResponseEntity::ok);
    }

}