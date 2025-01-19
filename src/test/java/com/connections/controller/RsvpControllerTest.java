package com.connections.controller;

import com.connections.dto.RsvpDTO;
import com.connections.service.IRsvpService;
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

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@WebFluxTest(RsvpController.class)
class RsvpControllerTest {

    // Client for test requests
    @Autowired
    private WebTestClient webTestClient;

    // Service Mock
    @MockBean
    private IRsvpService rsvpService;

    //
    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll_ReturnsFluxOfRsvps() throws Exception {
        // DTOs to be used as mock
        RsvpDTO dto1 = new RsvpDTO(1L, 1L, 1L, "Test Status", LocalDateTime.of(2025,1,18,11,46,31,496193900));
        RsvpDTO dto2 = new RsvpDTO(2L,  1L, 1L, "Test Status", LocalDateTime.of(2025,1,18,11,46,31,496193900));

        Mockito.when(rsvpService.findAll()).thenReturn(Flux.just(dto1, dto2));

        // Test GET /rsvp/all
        webTestClient.get()
                .uri("/rsvp/all")
                .exchange() // Send request
                .expectStatus().isOk() // Check return status
                .expectBodyList(RsvpDTO.class)
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void findById_ReturnsMonoOfRsvp() throws Exception {
        //DTO to be used as mock
        RsvpDTO dto = new RsvpDTO(1L,  1L, 1L, "Test Status", LocalDateTime.of(2025,1,18,11,46,31,496193900));

        Mockito.when(rsvpService.findById(1L)).thenReturn(Mono.just(dto));

        // Test GET /rsvp/id/1
        webTestClient.get()
                .uri("/rsvp/id/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(RsvpDTO.class)
                .isEqualTo(dto);

        Mockito.verify(rsvpService).findById(eq(1L));
    }

    @Test
    void save_ReturnsSavedRsvp() {
        // DTO to be used as mock and request
        RsvpDTO dtoToSave = new RsvpDTO(null,  1L, 1L, "Test Status", LocalDateTime.of(2025,1,18,11,46,31,496193900));
        RsvpDTO savedDto = new RsvpDTO(10L,   1L, 1L, "Test Status", LocalDateTime.of(2025,1,18,11,46,31,496193900));

        Mockito.when(rsvpService.save(any(RsvpDTO.class))).thenReturn(Mono.just(savedDto));

        // Test POST /rsvp, enviando JSON
        webTestClient.post()
                .uri("/rsvp")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dtoToSave)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RsvpDTO.class)
                .isEqualTo(savedDto);

        Mockito.verify(rsvpService).save(dtoToSave);
    }

    @Test
    void deleteById_ReturnsOk() {
        // Mock delete endpoint
        Mockito.when(rsvpService.delete(1L)).thenReturn(Mono.empty());

        // Test delete endpoint
        webTestClient.delete()
                .uri("/rsvp/id/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);

        Mockito.verify(rsvpService).delete(1L);
    }
}