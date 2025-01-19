package com.connections.service.impl;

import com.connections.dto.CommentDTO;
import com.connections.entity.CommentEntity;
import com.connections.repository.CommentRepository;
import com.connections.service.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of {@link ICommentService}.
 * <p>For method details, see the interface documentation.</p>
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {

    private final CommentRepository commentRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Flux<CommentDTO> findByPostId(Long postId) {
        return commentRepository.findByPostId(postId)
                .map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Flux<CommentDTO> findAll() {
        return commentRepository.findAll().map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<CommentDTO> findById(Long commentId) {
        return commentRepository.findById(commentId)
                .map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<CommentDTO> save(CommentDTO commentDto) {
        return commentRepository
                .save(this.toEntity(commentDto))
                .map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<Void> delete(Long commentId) {
        return commentRepository.deleteById(commentId);
    }

    /**
     * Converts CommentEntity to CommentDTO.
     */
    private CommentDTO toDto(CommentEntity entity) {
        return new CommentDTO(
                entity.getId(),
                entity.getContent(),
                entity.getEventId(),
                entity.getPostId(),
                entity.getCreatedAt(),
                entity.getCreatedBy()
        );
    }

    /**
     * Converts CommentDTO to CommentEntity.
     */
    private CommentEntity toEntity(CommentDTO dto) {
        return new CommentEntity(
                dto.getId(),
                dto.getContent(),
                dto.getEventId(),
                dto.getPostId(),
                dto.getCreatedAt(),
                dto.getCreatedBy()
        );
    }
}