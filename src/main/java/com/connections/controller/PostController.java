package com.connections.controller;

import com.connections.dto.PostDTO;
import com.connections.service.IPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller responsible for managing Posts.
 * Provides endpoints to list, retrieve, create, and delete Post resources,
 * as well as retrieve comments associated with a specific post.
 */

@RestController
@RequestMapping("/post")
@Tag(name = "Post Controller", description = "Endpoints for managing posts and their associated comments.")
public class PostController {

    final IPostService service;

    public PostController(final IPostService service) {
        this.service = service;
    }

    /**
     * Retrieves all posts in the system.
     *
     * @return A Flux stream of PostDTO objects.
     * @throws Exception if an error occurs during retrieval.
     */
    @Operation(
            summary = "Retrieve all posts",
            description = "Returns a reactive Flux of PostDTO containing all posts."
    )
    @GetMapping("/all")
    public Flux<PostDTO> findAll() throws Exception {
        return service.findAll();
    }

    /**
     * Finds a specific post by its identifier.
     *
     * @param id The ID of the post to retrieve.
     * @return A Mono emitting the found PostDTO, or empty if not found.
     * @throws Exception if an error occurs during retrieval.
     */
    @Operation(
            summary = "Find a post by ID",
            description = "Looks up a post by its ID and returns the matching PostDTO, if any."
    )
    @GetMapping("/id/{id}")
    public Mono<PostDTO> findById(@PathVariable("id") final Long id) throws Exception {
        return service.findById(id);
    }

    /**
     * Finds a specific post by its identifier, including its associated comments.
     *
     * @param id The ID of the post to retrieve along with comments.
     * @return A Mono emitting the PostDTO (with a list of CommentDTO) if found, or empty if not found.
     * @throws Exception if an error occurs during retrieval.
     */
    @Operation(
            summary = "Find a post and its comments by ID",
            description = "Retrieves a post by ID and includes all associated comments."
    )
    @GetMapping("/id/{id}/comments")
    public Mono<PostDTO> findWithCommentsById(@PathVariable("id") final Long id) throws Exception {
        return service.findPostWithComments(id);
    }

    /**
     * Saves (creates or updates) a Post resource.
     *
     * @param dto The PostDTO payload containing post details to be saved.
     * @return A Mono emitting the saved PostDTO.
     */
    @Operation(
            summary = "Save a post",
            description = "Creates or updates a post using the provided PostDTO."
    )
    @PostMapping
    public Mono<PostDTO> save(@RequestBody final PostDTO dto){
        return service.save(dto);
    }

    /**
     * Deletes a post identified by its ID.
     *
     * @param id The ID of the post to delete.
     * @return A Mono emitting an HTTP 200 (OK) if deletion was successful.
     */
    @Operation(
            summary = "Delete a post by ID",
            description = "Removes a post from the system using its ID."
    )
    @DeleteMapping("/id/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") final Long id){
        return this.service.delete(id)
                .map(ResponseEntity::ok);
    }

}