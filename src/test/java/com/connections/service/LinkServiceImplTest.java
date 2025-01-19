package com.connections.service;

import com.connections.dto.LinkDTO;
import com.connections.entity.LinkEntity;
import com.connections.repository.LinkRepository;
import com.connections.service.impl.LinkServiceImpl;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class LinkServiceImplTest {

    @Mock
    private LinkRepository linkRepository;

    @InjectMocks
    private LinkServiceImpl linkService;

    private LinkEntity entity1;
    private LinkEntity entity2;

    private LinkDTO dto1;
    private LinkDTO dto2;

    @BeforeEach
    void setup() {
        entity1 = new LinkEntity(1L,"test.com.br", 1L,  "Youtube");
        entity2 = new LinkEntity(2L, "test.com.br", 1L, "Instagram");

        dto1 = new LinkDTO(1L, "test.com.br", 1L, "Youtube");
        dto2 = new LinkDTO(2L, "test.com.br", 1L, "Instagram");
    }

    @Test
    void findAll_ShouldReturnFluxOfLinkDTO() {
        // Mock Repository call
        Mockito.when(linkRepository.findAll()).thenReturn(Flux.just(entity1, entity2));

        // Service Call
        Flux<LinkDTO> result = linkService.findAll();

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(dto1)
                .expectNext(dto2)
                .verifyComplete();

        // Check if repository.findAll() was called
        Mockito.verify(linkRepository).findAll();
    }

    @Test
    void findById_ShouldReturnMonoOfLinkDTO() {
        // Mock Repository call
        Mockito.when(linkRepository.findById(1L)).thenReturn(Mono.just(entity1));

        // Service Call
        Mono<LinkDTO> result = linkService.findById(1L);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(dto1)
                .verifyComplete();

        // Check if repository.findById() was called
        Mockito.verify(linkRepository).findById(eq(1L));
    }

    @Test
    void save_ShouldReturnSavedLinkDTO() {
        // Objects to use as Mock
        LinkEntity savedEntity = new LinkEntity(1L, "test.com.br", 1L, "Youtube");
        LinkDTO dtoToSave = new LinkDTO(null, "test.com.br", 1L, "Youtube");
        LinkDTO expectedDTO = new LinkDTO(1L, "test.com.br", 1L, "Youtube");

        //Mock Repository call
        Mockito.when(linkRepository.save(any(LinkEntity.class)))
                .thenReturn(Mono.just(savedEntity));

        //Service Call
        Mono<LinkDTO> result = linkService.save(dtoToSave);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(expectedDTO)
                .verifyComplete();

        // Check if save was called
        Mockito.verify(linkRepository).save(any(LinkEntity.class));
    }

    @Test
    void delete_ShouldCallRepositoryAndReturnMonoVoid() {
        //Mock Repository call
        Mockito.when(linkRepository.deleteById(1L)).thenReturn(Mono.empty());

        //Service Call
        Mono<Void> result = linkService.delete(1L);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .verifyComplete();

        // Check if delete was called
        Mockito.verify(linkRepository).deleteById(eq(1L));
    }
}