package com.connections.controller;

import com.connections.dto.EventDTO;
import com.connections.dto.EventDTO;
import com.connections.service.EventService;
import com.connections.service.EventService;
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

@WebFluxTest(EventController.class)
class EventControllerTest {

    // Client for test requests
    @Autowired
    private WebTestClient webTestClient;

    // Service Mock
    @MockBean
    private EventService eventService;

    //
    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll_ReturnsFluxOfEvents() throws Exception {
        // DTOs to be used as mock
        EventDTO dto1 = new EventDTO(1L, "Event One", "Test Description", LocalDateTime.now(), 1L);
        EventDTO dto2 = new EventDTO(2L, "Event Two", "Test Description", LocalDateTime.now(), 1L);

        Mockito.when(eventService.findAll()).thenReturn(Flux.just(dto1, dto2));

        // Test GET /event/all
        webTestClient.get()
                .uri("/event/all")
                .exchange() // Send request
                .expectStatus().isOk() // Check return status
                .expectBodyList(EventDTO.class)
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void findById_ReturnsMonoOfEvent() throws Exception {
        //DTO to be used as mock
        EventDTO dto = new EventDTO(1L, "Event One", "Test Description", LocalDateTime.now(), 1L);

        Mockito.when(eventService.findById(1L)).thenReturn(Mono.just(dto));

        // Test GET /event/id/1
        webTestClient.get()
                .uri("/event/id/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(EventDTO.class)
                .isEqualTo(dto);

        Mockito.verify(eventService).findById(eq(1L));
    }

    @Test
    void save_ReturnsSavedEvent() {
        // DTO to be used as mock and request
        EventDTO dtoToSave = new EventDTO(null, "Event One", "Test Description", LocalDateTime.now(), 1L);
        EventDTO savedDto = new EventDTO(10L, "Event One", "Test Description", LocalDateTime.now(), 1L);

        Mockito.when(eventService.save(any(EventDTO.class))).thenReturn(Mono.just(savedDto));

        // Test POST /event, enviando JSON
        webTestClient.post()
                .uri("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dtoToSave)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EventDTO.class)
                .isEqualTo(savedDto);

        Mockito.verify(eventService).save(dtoToSave);
    }

    @Test
    void deleteById_ReturnsOk() {
        // Mock delete endpoint
        Mockito.when(eventService.delete(1L)).thenReturn(Mono.empty());

        // Test delete endpoint
        webTestClient.delete()
                .uri("/event/id/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);

        Mockito.verify(eventService).delete(1L);
    }
}