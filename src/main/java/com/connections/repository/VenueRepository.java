package com.connections.repository;

import com.connections.entity.VenueEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface VenueRepository extends ReactiveCrudRepository<VenueEntity, Long> {

}
