package com.connections.service;

import com.connections.dto.EventDTO;
import com.connections.entity.EventEntity;
import com.connections.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private EventEntity entity1;
    private EventEntity entity2;

    private EventDTO dto1;
    private EventDTO dto2;

    @BeforeEach
    void setup() {
        entity1 = new EventEntity(1L, "Event One", "Test Description", LocalDateTime.of(2025,1,18,11,46,31,496193900), 1L);
        entity2 = new EventEntity(2L, "Event Two", "Test Description", LocalDateTime.of(2025,1,18,11,46,31,496193900), 1L);

        dto1 = new EventDTO(1L, "Event One", "Test Description", LocalDateTime.of(2025,1,18,11,46,31,496193900), 1L);
        dto2 = new EventDTO(2L, "Event Two", "Test Description", LocalDateTime.of(2025,1,18,11,46,31,496193900), 1L);
    }

    @Test
    void findAll_ShouldReturnFluxOfEventDTO() {
        // Mock Repository call
        Mockito.when(eventRepository.findAll()).thenReturn(Flux.just(entity1, entity2));

        // Service Call
        Flux<EventDTO> result = eventService.findAll();

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(dto1)
                .expectNext(dto2)
                .verifyComplete();

        // Check if repository.findAll() was called
        Mockito.verify(eventRepository).findAll();
    }

    @Test
    void findById_ShouldReturnMonoOfEventDTO() {
        // Mock Repository call
        Mockito.when(eventRepository.findById(1L)).thenReturn(Mono.just(entity1));

        // Service Call
        Mono<EventDTO> result = eventService.findById(1L);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(dto1)
                .verifyComplete();

        // Check if repository.findById() was called
        Mockito.verify(eventRepository).findById(eq(1L));
    }

    @Test
    void save_ShouldReturnSavedEventDTO() {
        // Objects to use as Mock
        EventEntity savedEntity = new EventEntity(1L, "Event One", "Test Description", LocalDateTime.now(), 1L);
        EventDTO dtoToSave = new EventDTO(null, "Event One", "Test Description", LocalDateTime.now(), 1L);
        EventDTO expectedDTO = new EventDTO(1L, "Event One", "Test Description", LocalDateTime.now(), 1L);

        //Mock Repository call
        Mockito.when(eventRepository.save(any(EventEntity.class)))
                .thenReturn(Mono.just(savedEntity));

        //Service Call
        Mono<EventDTO> result = eventService.save(dtoToSave);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(expectedDTO)
                .verifyComplete();

        // Check if save was called
        Mockito.verify(eventRepository).save(any(EventEntity.class));
    }

    @Test
    void delete_ShouldCallRepositoryAndReturnMonoVoid() {
        //Mock Repository call
        Mockito.when(eventRepository.deleteById(1L)).thenReturn(Mono.empty());

        //Service Call
        Mono<Void> result = eventService.delete(1L);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .verifyComplete();

        // Check if delete was called
        Mockito.verify(eventRepository).deleteById(eq(1L));
    }
}