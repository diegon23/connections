package com.connections.service;

import com.connections.dto.CommentDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Defines the contract for comment-related operations
 * within the system. Provides methods to create, read,
 * update, and delete comments, as well as specialized
 * lookups (e.g., by post ID).
 */
public interface ICommentService {

    /**
     * Retrieves all comments associated with a specific post.
     *
     * @param postId the ID of the post whose comments should be fetched
     * @return a Flux of CommentDTO objects belonging to the given post
     */
    Flux<CommentDTO> findByPostId(Long postId);

    /**
     * Retrieves all comments in the system.
     *
     * @return a Flux of all CommentDTO objects
     */
    Flux<CommentDTO> findAll();

    /**
     * Finds a single comment by its unique identifier.
     *
     * @param commentId the ID of the comment to retrieve
     * @return a Mono emitting the found CommentDTO, or empty if not found
     */
    Mono<CommentDTO> findById(Long commentId);

    /**
     * Saves (creates or updates) a comment in the system.
     *
     * @param commentDto the data transfer object containing comment details
     * @return a Mono emitting the saved CommentDTO
     */
    Mono<CommentDTO> save(CommentDTO commentDto);

    /**
     * Deletes a comment by its unique identifier.
     *
     * @param commentId the ID of the comment to remove
     * @return a Mono signaling completion of the deletion operation
     */
    Mono<Void> delete(Long commentId);
}