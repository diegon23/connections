package com.connections.repository;

import com.connections.entity.CommentEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CommentRepository extends ReactiveCrudRepository<CommentEntity, Long> {

    // Find all comments for a specific post using postId
    Flux<CommentEntity> findByPostId(Long postId);
}
