package com.connections.service;

import com.connections.dto.CommentDTO;
import com.connections.dto.PostDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Defines the contract for post-related operations.
 * Provides methods to create, retrieve, update, and delete posts,
 * as well as retrieving comments associated with a post.
 */
public interface IPostService {

    /**
     * Retrieves a post by its ID, along with any associated comments.
     *
     * @param postId the ID of the post to retrieve
     * @return a Mono emitting a PostDTO containing the post data and a list of its comments
     */
    Mono<PostDTO> findPostWithComments(Long postId);

    /**
     * Retrieves all posts in the system.
     *
     * @return a Flux of PostDTO representing all posts
     */
    Flux<PostDTO> findAll();

    /**
     * Finds a post by its unique identifier.
     *
     * @param postId the ID of the post to look up
     * @return a Mono emitting the found PostDTO, or empty if no match is found
     */
    Mono<PostDTO> findById(Long postId);

    /**
     * Saves (creates or updates) a post in the system.
     *
     * @param dto the PostDTO containing post details
     * @return a Mono emitting the saved PostDTO
     */
    Mono<PostDTO> save(PostDTO dto);

    /**
     * Deletes a post by its unique identifier.
     *
     * @param postId the ID of the post to remove
     * @return a Mono signaling completion of the deletion operation
     */
    Mono<Void> delete(Long postId);
}