package com.connections.service;

import com.connections.dto.EventDTO;
import com.connections.entity.EventEntity;
import com.connections.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Flux<EventDTO> findAll(){
        return eventRepository.findAll().map(this::toDto);
    }

    public Mono<EventDTO> findById(Long eventId){
        return eventRepository.findById(eventId)
                .map(this::toDto);
    }

    public Mono<EventDTO> save(EventDTO eventDto){
        return eventRepository
                .save(this.toEntity(eventDto))
                .map(this::toDto);
    }

    public Mono<Void> delete(Long eventId){
        return eventRepository.deleteById(eventId);
    }

    private EventDTO toDto(EventEntity entity){
        return new EventDTO(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getDateTime(), entity.getVenueId());
    }

    private EventEntity toEntity(EventDTO dto){
        return new EventEntity(dto.getId(), dto.getTitle(), dto.getDescription(), dto.getDateTime(), dto.getVenueId());
    }
}