package com.connections.repository;

import com.connections.entity.EventEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EventRepository extends ReactiveCrudRepository<EventEntity, Long> {

    // Find all events for a specific venue using venueId
    Flux<EventEntity> findByVenueId(Long venueId);

}
