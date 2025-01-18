package com.connections.repository;

import com.connections.entity.LinkEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface LinkRepository extends ReactiveCrudRepository<LinkEntity, Long> {

    // Find all links for a specific creator using creatorId
    Flux<LinkEntity> findByCreatorId(Long creatorId);
}
