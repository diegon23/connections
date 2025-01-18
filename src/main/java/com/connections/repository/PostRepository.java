package com.connections.repository;

import com.connections.entity.PostEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PostRepository extends ReactiveCrudRepository<PostEntity, Long> {

    // Find all posts for a specific creator using createdBy
    Flux<PostEntity> findByCreatedBy(String creatorBy);

}
