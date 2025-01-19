package com.connections.service.impl;

import com.connections.dto.EventDTO;
import com.connections.entity.EventEntity;
import com.connections.repository.EventRepository;
import com.connections.service.IEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of {@link IEventService}.
 * <p>For method details, see the interface documentation.</p>
 */
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements IEventService {

    private final EventRepository eventRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Flux<EventDTO> findAll() {
        return eventRepository.findAll().map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<EventDTO> findById(Long eventId) {
        return eventRepository.findById(eventId)
                .map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<EventDTO> save(EventDTO eventDto) {
        return eventRepository
                .save(this.toEntity(eventDto))
                .map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<Void> delete(Long eventId) {
        return eventRepository.deleteById(eventId);
    }

    /**
     * Converts EventEntity to EventDTO.
     */
    private EventDTO toDto(EventEntity entity) {
        return new EventDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDateTime(),
                entity.getVenueId()
        );
    }

    /**
     * Converts EventDTO to EventEntity.
     */
    private EventEntity toEntity(EventDTO dto) {
        return new EventEntity(
                dto.getId(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getDateTime(),
                dto.getVenueId()
        );
    }
}
