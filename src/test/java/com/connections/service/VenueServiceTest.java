package com.connections.service;

import com.connections.dto.VenueDTO;
import com.connections.entity.VenueEntity;
import com.connections.repository.VenueRepository;
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
class VenueServiceTest {

    @Mock
    private VenueRepository venueRepository;

    @InjectMocks
    private VenueService venueService;

    private VenueEntity entity1;
    private VenueEntity entity2;

    private VenueDTO dto1;
    private VenueDTO dto2;

    @BeforeEach
    void setup() {
        entity1 = new VenueEntity(1L, "Venue 1", "Test Location", 1000);
        entity2 = new VenueEntity(2L, "Venue 2", "Test Location", 1000);

        dto1 = new VenueDTO(1L, "Venue 1", "Test Location", 1000);
        dto2 = new VenueDTO(2L, "Venue 2", "Test Location", 1000);
    }

    @Test
    void findAll_ShouldReturnFluxOfVenueDTO() {
        // Mock Repository call
        Mockito.when(venueRepository.findAll()).thenReturn(Flux.just(entity1, entity2));

        // Service Call
        Flux<VenueDTO> result = venueService.findAll();

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(dto1)
                .expectNext(dto2)
                .verifyComplete();

        // Check if repository.findAll() was called
        Mockito.verify(venueRepository).findAll();
    }

    @Test
    void findById_ShouldReturnMonoOfVenueDTO() {
        // Mock Repository call
        Mockito.when(venueRepository.findById(1L)).thenReturn(Mono.just(entity1));

        // Service Call
        Mono<VenueDTO> result = venueService.findById(1L);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(dto1)
                .verifyComplete();

        // Check if repository.findById() was called
        Mockito.verify(venueRepository).findById(eq(1L));
    }

    @Test
    void save_ShouldReturnSavedVenueDTO() {
        // Objects to use as Mock
        VenueEntity savedEntity   = new VenueEntity(3L, "Venue 1", "Test Location", 1000);
        VenueDTO dtoToSave = new VenueDTO(null, "Venue 1", "Test Location", 1000);
        VenueDTO expectedDTO = new VenueDTO(3L, "Venue 1", "Test Location", 1000);

        //Mock Repository call
        Mockito.when(venueRepository.save(any(VenueEntity.class)))
                .thenReturn(Mono.just(savedEntity));

        //Service Call
        Mono<VenueDTO> result = venueService.save(dtoToSave);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(expectedDTO)
                .verifyComplete();

        // Check if save was called
        Mockito.verify(venueRepository).save(any(VenueEntity.class));
    }

    @Test
    void delete_ShouldCallRepositoryAndReturnMonoVoid() {
        //Mock Repository call
        Mockito.when(venueRepository.deleteById(1L)).thenReturn(Mono.empty());

        //Service Call
        Mono<Void> result = venueService.delete(1L);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .verifyComplete();

        // Check if delete was called
        Mockito.verify(venueRepository).deleteById(eq(1L));
    }
}