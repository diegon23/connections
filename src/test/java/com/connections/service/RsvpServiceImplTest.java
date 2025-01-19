package com.connections.service;

import com.connections.dto.RsvpDTO;
import com.connections.entity.RsvpEntity;
import com.connections.repository.RsvpRepository;
import com.connections.service.impl.RsvpServiceImpl;
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
class RsvpServiceImplTest {

    @Mock
    private RsvpRepository rsvpRepository;

    @InjectMocks
    private RsvpServiceImpl rsvpService;

    private RsvpEntity entity1;
    private RsvpEntity entity2;

    private RsvpDTO dto1;
    private RsvpDTO dto2;

    @BeforeEach
    void setup() {
        entity1 = new RsvpEntity(1L, 1L, 1L, "Test Status", LocalDateTime.of(2025,1,18,11,46,31,496193900));
        entity2 = new RsvpEntity(2L, 1L, 1L, "Test Status", LocalDateTime.of(2025,1,18,11,46,31,496193900));

        dto1 = new RsvpDTO(1L, 1L, 1L, "Test Status", LocalDateTime.of(2025,1,18,11,46,31,496193900));
        dto2 = new RsvpDTO(2L, 1L, 1L, "Test Status", LocalDateTime.of(2025,1,18,11,46,31,496193900));
    }

    @Test
    void findAll_ShouldReturnFluxOfRsvpDTO() {
        // Mock Repository call
        Mockito.when(rsvpRepository.findAll()).thenReturn(Flux.just(entity1, entity2));

        // Service Call
        Flux<RsvpDTO> result = rsvpService.findAll();

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(dto1)
                .expectNext(dto2)
                .verifyComplete();

        // Check if repository.findAll() was called
        Mockito.verify(rsvpRepository).findAll();
    }

    @Test
    void findById_ShouldReturnMonoOfRsvpDTO() {
        // Mock Repository call
        Mockito.when(rsvpRepository.findById(1L)).thenReturn(Mono.just(entity1));

        // Service Call
        Mono<RsvpDTO> result = rsvpService.findById(1L);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(dto1)
                .verifyComplete();

        // Check if repository.findById() was called
        Mockito.verify(rsvpRepository).findById(eq(1L));
    }

    @Test
    void save_ShouldReturnSavedRsvpDTO() {
        // Objects to use as Mock
        RsvpEntity savedEntity   = new RsvpEntity(3L, 1L, 1L, "Test Status", LocalDateTime.of(2025,1,18,11,46,31,496193900));
        RsvpDTO dtoToSave = new RsvpDTO(null, 1L, 1L, "Test Status", LocalDateTime.of(2025,1,18,11,46,31,496193900));
        RsvpDTO expectedDTO = new RsvpDTO(3L, 1L, 1L, "Test Status", LocalDateTime.of(2025,1,18,11,46,31,496193900));

        //Mock Repository call
        Mockito.when(rsvpRepository.save(any(RsvpEntity.class)))
                .thenReturn(Mono.just(savedEntity));

        //Service Call
        Mono<RsvpDTO> result = rsvpService.save(dtoToSave);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(expectedDTO)
                .verifyComplete();

        // Check if save was called
        Mockito.verify(rsvpRepository).save(any(RsvpEntity.class));
    }

    @Test
    void delete_ShouldCallRepositoryAndReturnMonoVoid() {
        //Mock Repository call
        Mockito.when(rsvpRepository.deleteById(1L)).thenReturn(Mono.empty());

        //Service Call
        Mono<Void> result = rsvpService.delete(1L);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .verifyComplete();

        // Check if delete was called
        Mockito.verify(rsvpRepository).deleteById(eq(1L));
    }
}