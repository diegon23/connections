package com.connections.service.impl;

import com.connections.dto.VenueDTO;
import com.connections.entity.VenueEntity;
import com.connections.repository.VenueRepository;
import com.connections.service.IVenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of {@link IVenueService}.
 * <p>For method details, see the interface documentation.</p>
 */
@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements IVenueService {

    private final VenueRepository venueRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Flux<VenueDTO> findAll() {
        return venueRepository.findAll().map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<VenueDTO> findById(Long venueId) {
        return venueRepository.findById(venueId)
                .map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<VenueDTO> save(VenueDTO venue) {
        return venueRepository
                .save(this.toEntity(venue))
                .map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<Void> delete(Long venueId) {
        return venueRepository.deleteById(venueId);
    }

    /**
     * Converts VenueEntity to VenueDTO.
     */
    private VenueDTO toDto(VenueEntity entity) {
        return new VenueDTO(
                entity.getId(),
                entity.getName(),
                entity.getLocation(),
                entity.getCapacity()
        );
    }

    /**
     * Converts VenueDTO to VenueEntity.
     */
    private VenueEntity toEntity(VenueDTO dto) {
        return new VenueEntity(
                dto.getId(),
                dto.getName(),
                dto.getLocation(),
                dto.getCapacity()
        );
    }
}
