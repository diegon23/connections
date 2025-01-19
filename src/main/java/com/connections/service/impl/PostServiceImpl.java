package com.connections.service.impl;

import com.connections.dto.CommentDTO;
import com.connections.dto.PostDTO;
import com.connections.entity.PostEntity;
import com.connections.repository.PostRepository;
import com.connections.service.ICommentService;
import com.connections.service.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of {@link IPostService}.
 * <p>For method details, see the interface documentation.</p>
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {

    private final PostRepository postRepository;
    private final ICommentService commentService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<PostDTO> findPostWithComments(Long postId) {
        // 1. Find post
        Mono<PostDTO> postMono = this.findById(postId);
        // 2. Find comments for that post
        Flux<CommentDTO> commentFlux = commentService.findByPostId(postId);

        // 3. Combine
        return postMono.zipWith(commentFlux.collectList())
                .map(tuple -> {
                    PostDTO post = tuple.getT1();
                    var comments = tuple.getT2();
                    return new PostDTO(
                            post.getId(),
                            post.getContent(),
                            post.getCreatedAt(),
                            post.getCreatedBy(),
                            comments
                    );
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Flux<PostDTO> findAll() {
        return postRepository.findAll().map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<PostDTO> findById(Long postId) {
        return postRepository.findById(postId)
                .map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<PostDTO> save(PostDTO dto) {
        return postRepository
                .save(this.toEntity(dto))
                .map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<Void> delete(Long postId) {
        return postRepository.deleteById(postId);
    }

    /**
     * Converts PostEntity to PostDTO.
     */
    private PostDTO toDto(PostEntity entity) {
        return new PostDTO(
                entity.getId(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy()
        );
    }

    /**
     * Converts PostDTO to PostEntity.
     */
    private PostEntity toEntity(PostDTO dto) {
        return new PostEntity(
                dto.getId(),
                dto.getContent(),
                dto.getCreatedAt(),
                dto.getCreatedBy()
        );
    }
}
