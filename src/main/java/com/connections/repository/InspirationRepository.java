package com.connections.repository;

import com.connections.entity.InspirationEntity;
import com.connections.entity.PostEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface InspirationRepository extends ReactiveCrudRepository<InspirationEntity, Long> {

    // Find all inspirations for a specific creator using creatorID
    Flux<InspirationEntity> findByCreatorId(Long creatorID);
}
