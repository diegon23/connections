package com.connections.repository;

import com.connections.entity.CreatorEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CreatorRepository extends ReactiveCrudRepository<CreatorEntity, Long> {

}
