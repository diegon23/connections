package com.connections.service;

import com.connections.dto.CreatorDTO;
import com.connections.dto.InspirationDTO;
import com.connections.entity.CreatorEntity;
import com.connections.entity.InspirationEntity;
import com.connections.external.musicbrainz.dto.MusicBrainzArtist;
import com.connections.repository.CreatorRepository;
import com.connections.repository.InspirationRepository;
import com.connections.service.impl.CreatorServiceImpl;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class CreatorServiceImplTest {

    @Mock
    private CreatorRepository creatorRepository;

    @Mock
    private InspirationRepository inspirationRepository;

    @Mock
    private IInspirationService inspirationService;

    @InjectMocks
    private CreatorServiceImpl creatorService;

    private CreatorEntity entity1;
    private CreatorEntity entity2;

    private CreatorDTO dto1;
    private CreatorDTO dto2;

    private MusicBrainzArtist dummyArtist;

    private InspirationEntity dummyInspirationEntity;

    @BeforeEach
    void setup() {
        entity1 = new CreatorEntity(1L, "Alice", "she/her", "Bio Alice");
        entity2 = new CreatorEntity(2L, "Bob", "he/him", "Bio Bob");

        dto1 = new CreatorDTO(1L, "Alice", "she/her", "Bio Alice");
        dto2 = new CreatorDTO(2L, "Bob",   "he/him",  "Bio Bob");

        // A sample external artist data
        dummyArtist = new MusicBrainzArtist();
        dummyArtist.setId("someInspirationId");
        dummyArtist.setName("Radiohead");

        // A sample InspirationEntity to represent what's saved in DB
        dummyInspirationEntity = new InspirationEntity();
        dummyInspirationEntity.setId(10L);
        dummyInspirationEntity.setCreatorId(1L);
        dummyInspirationEntity.setInspirationName("Radiohead");
    }

    @Test
    void findAll_ShouldReturnFluxOfCreatorDTO() {
        // Mock Repository call
        Mockito.when(creatorRepository.findAll()).thenReturn(Flux.just(entity1, entity2));

        // Service Call
        Flux<CreatorDTO> result = creatorService.findAll();

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(dto1)
                .expectNext(dto2)
                .verifyComplete();

        // Check if repository.findAll() was called
        Mockito.verify(creatorRepository).findAll();
    }

    @Test
    void findById_ShouldReturnMonoOfCreatorDTO() {
        //DTO to be used as Mock
        InspirationEntity inspirationEntity = new InspirationEntity(1L, 1L, "Inspiration Test");
        dto1.setInspirations(List.of(new InspirationDTO(1L, "Inspiration Test")));

        // Mock Repository call
        Mockito.when(creatorRepository.findById(1L)).thenReturn(Mono.just(entity1));
        Mockito.when(inspirationRepository.findByCreatorId(1L)).thenReturn(Flux.just(inspirationEntity));

        // Service Call
        Mono<CreatorDTO> result = creatorService.findById(1L);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(dto1)
                .verifyComplete();

        // Check if repository.findById() was called
        Mockito.verify(creatorRepository).findById(eq(1L));
    }

    @Test
    void testAddInspirationToCreator() {
        // Mock creatorRepository.findById(...) -> Mono.just(entity1)
        Mockito.when(creatorRepository.findById(1L))
                .thenReturn(Mono.just(entity1));

        // Mock inspirationService.findArtistById(...) -> Mono.just(dummyArtist)
        Mockito.when(inspirationService.findArtistById("someInspirationId"))
                .thenReturn(Mono.just(dummyArtist));

        // Mock inspirationRepository.save(...) -> Mono.just(dummyInspirationEntity)
        Mockito.when(inspirationRepository.save(org.mockito.ArgumentMatchers.any(InspirationEntity.class)))
                .thenReturn(Mono.just(dummyInspirationEntity));

        // Mock inspirationRepository.findByCreatorId(Long) --> Flux.empty()
        Mockito.when(inspirationRepository.findByCreatorId(any()))
                .thenReturn(Flux.empty());

        // Call the method under test
        Mono<InspirationDTO> resultMono = creatorService.addInspirationToCreator(1L, "someInspirationId");

        // Verify with StepVerifier
        StepVerifier.create(resultMono)
                .assertNext(inspiration -> {
                    // The method returns an InspirationDTO
                    // Confirm the ID, name, etc. match what we expect
                    org.junit.jupiter.api.Assertions.assertEquals(10L, inspiration.getId());
                    org.junit.jupiter.api.Assertions.assertEquals("Radiohead", inspiration.getName());
                })
                .verifyComplete();
    }

    @Test
    void save_ShouldReturnSavedCreatorDTO() {
        // Objects to use as Mock
        CreatorEntity savedEntity   = new CreatorEntity(3L, "Charlie", "they/them", "Bio Charlie");
        CreatorDTO dtoToSave = new CreatorDTO(null, "Charlie", "they/them", "Bio Charlie");
        CreatorDTO expectedDTO = new CreatorDTO(3L, "Charlie", "they/them", "Bio Charlie");

        //Mock Repository call
        Mockito.when(creatorRepository.save(any(CreatorEntity.class)))
                .thenReturn(Mono.just(savedEntity));

        //Service Call
        Mono<CreatorDTO> result = creatorService.save(dtoToSave);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(expectedDTO)
                .verifyComplete();

        // Check if save was called
        Mockito.verify(creatorRepository).save(any(CreatorEntity.class));
    }

    @Test
    void delete_ShouldCallRepositoryAndReturnMonoVoid() {
        //Mock Repository call
        Mockito.when(creatorRepository.deleteById(1L)).thenReturn(Mono.empty());

        //Service Call
        Mono<Void> result = creatorService.delete(1L);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .verifyComplete();

        // Check if delete was called
        Mockito.verify(creatorRepository).deleteById(eq(1L));
    }
}