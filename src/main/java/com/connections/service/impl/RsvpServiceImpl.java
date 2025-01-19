package com.connections.service.impl;

import com.connections.dto.RsvpDTO;
import com.connections.entity.RsvpEntity;
import com.connections.repository.RsvpRepository;
import com.connections.service.IRsvpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of {@link IRsvpService}.
 * <p>For method details, see the interface documentation.</p>
 */
@Service
@RequiredArgsConstructor
public class RsvpServiceImpl implements IRsvpService {

    private final RsvpRepository rsvpRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Flux<RsvpDTO> findAll() {
        return rsvpRepository.findAll().map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<RsvpDTO> findById(Long rsvpId) {
        return rsvpRepository.findById(rsvpId)
                .map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<RsvpDTO> save(RsvpDTO rsvp) {
        return rsvpRepository
                .save(this.toEntity(rsvp))
                .map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<Void> delete(Long rsvpId) {
        return rsvpRepository.deleteById(rsvpId);
    }

    /**
     * Converts RsvpEntity to RsvpDTO.
     */
    private RsvpDTO toDto(RsvpEntity entity) {
        return new RsvpDTO(
                entity.getId(),
                entity.getEventId(),
                entity.getCreatorId(),
                entity.getStatus(),
                entity.getLastUpdated()
        );
    }

    /**
     * Converts RsvpDTO to RsvpEntity.
     */
    private RsvpEntity toEntity(RsvpDTO dto) {
        return new RsvpEntity(
                dto.getId(),
                dto.getEventId(),
                dto.getCreatorId(),
                dto.getStatus(),
                dto.getLastUpdated()
        );
    }
}
