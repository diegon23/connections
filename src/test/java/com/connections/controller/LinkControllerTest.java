package com.connections.controller;

import com.connections.dto.LinkDTO;
import com.connections.service.ILinkService;
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

@WebFluxTest(LinkController.class)
class LinkControllerTest {

    // Client for test requests
    @Autowired
    private WebTestClient webTestClient;

    // Service Mock
    @MockBean
    private ILinkService linkService;

    //
    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll_ReturnsFluxOfLinks() throws Exception {
        // DTOs to be used as mock
        LinkDTO dto1 = new LinkDTO(1L, "test.com.br", 1L, "Link 1");
        LinkDTO dto2 = new LinkDTO(2L, "test.com.br", 1L, "Link 2");

        Mockito.when(linkService.findAll()).thenReturn(Flux.just(dto1, dto2));

        // Test GET /link/all
        webTestClient.get()
                .uri("/link/all")
                .exchange() // Send request
                .expectStatus().isOk() // Check return status
                .expectBodyList(LinkDTO.class)
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void findById_ReturnsMonoOfLink() throws Exception {
        //DTO to be used as mock
        LinkDTO dto = new LinkDTO(1L, "test.com.br", 1L, "Link 1");

        Mockito.when(linkService.findById(1L)).thenReturn(Mono.just(dto));

        // Test GET /link/id/1
        webTestClient.get()
                .uri("/link/id/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(LinkDTO.class)
                .isEqualTo(dto);

        Mockito.verify(linkService).findById(eq(1L));
    }

    @Test
    void save_ReturnsSavedLink() {
        // DTO to be used as mock and request
        LinkDTO dtoToSave = new LinkDTO(null, "test.com.br", 1L, "Link 1");
        LinkDTO savedDto = new LinkDTO(10L, "test.com.br",  1L, "Link 1");

        Mockito.when(linkService.save(any(LinkDTO.class))).thenReturn(Mono.just(savedDto));

        // Test POST /link, enviando JSON
        webTestClient.post()
                .uri("/link")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dtoToSave)
                .exchange()
                .expectStatus().isOk()
                .expectBody(LinkDTO.class)
                .isEqualTo(savedDto);

        Mockito.verify(linkService).save(dtoToSave);
    }

    @Test
    void deleteById_ReturnsOk() {
        // Mock delete endpoint
        Mockito.when(linkService.delete(1L)).thenReturn(Mono.empty());

        // Test delete endpoint
        webTestClient.delete()
                .uri("/link/id/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);

        Mockito.verify(linkService).delete(1L);
    }
}