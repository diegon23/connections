package com.connections.repository;

import com.connections.entity.RsvpEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface RsvpRepository extends ReactiveCrudRepository<RsvpEntity, Long> {

    // Find all RSVP for a specific creator using creatorId
    Flux<RsvpEntity> findByCreatorId(Long creatorId);

    // Find all RSVP for a specific event using eventId
    Flux<RsvpEntity> findByEventId(Long eventId);
}