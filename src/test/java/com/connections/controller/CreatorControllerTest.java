package com.connections.controller;

import com.connections.dto.CreatorDTO;
import com.connections.service.ICreatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@WebFluxTest(CreatorController.class)
class CreatorControllerTest {

    // Client for test requests
    @Autowired
    private WebTestClient webTestClient;

    // Service Mock
    @MockBean
    private ICreatorService creatorService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void findAll_ReturnsFluxOfCreators() throws Exception {
        // DTOs to be used as mock
        CreatorDTO dto1 = new CreatorDTO(1L, "Creator One", "test/test", "Bio test 1");
        CreatorDTO dto2 = new CreatorDTO(2L, "Creator Two", "test/test", "Bio test 2");

        Mockito.when(creatorService.findAll()).thenReturn(Flux.just(dto1, dto2));

        // Test GET /creator/all
        webTestClient.get()
                .uri("/creator/all")
                .exchange() // Send request
                .expectStatus().isOk() // Check return status
                .expectBodyList(CreatorDTO.class)
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void findById_ReturnsMonoOfCreator() throws Exception {
        //DTO to be used as mock
        CreatorDTO dto = new CreatorDTO(1L, "Creator One", "test/test", "Bio test 1");

        Mockito.when(creatorService.findById(1L)).thenReturn(Mono.just(dto));

        // Test GET /creator/id/1
        webTestClient.get()
                .uri("/creator/id/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CreatorDTO.class)
                .isEqualTo(dto);

        Mockito.verify(creatorService).findById(eq(1L));
    }

    @Test
    void save_ReturnsSavedCreator() {
        // DTO to be used as mock and request
        CreatorDTO dtoToSave = new CreatorDTO(null, "New Creator", "test/test", "Bio test 1");
        CreatorDTO savedDto = new CreatorDTO(10L, "New Creator", "test/test", "Bio test 1");

        Mockito.when(creatorService.save(any(CreatorDTO.class))).thenReturn(Mono.just(savedDto));

        // Test POST /creator, enviando JSON
        webTestClient.post()
                .uri("/creator")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dtoToSave)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CreatorDTO.class)
                .isEqualTo(savedDto);

        Mockito.verify(creatorService).save(dtoToSave);
    }

    @Test
    void deleteById_ReturnsOk() {
        // Mock delete endpoint
        Mockito.when(creatorService.delete(1L)).thenReturn(Mono.empty());

        // Test delete endpoint
        webTestClient.delete()
                .uri("/creator/id/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);

        Mockito.verify(creatorService).delete(1L);
    }
}