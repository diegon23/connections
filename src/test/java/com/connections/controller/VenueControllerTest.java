package com.connections.controller;

import com.connections.dto.VenueDTO;
import com.connections.service.VenueService;
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

@WebFluxTest(VenueController.class)
class VenueControllerTest {

    // Client for test requests
    @Autowired
    private WebTestClient webTestClient;

    // Service Mock
    @MockBean
    private VenueService venueService;

    //
    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll_ReturnsFluxOfVenues() throws Exception {
        // DTOs to be used as mock
        VenueDTO dto1 = new VenueDTO(1L, "Venue 1", "Test Location", 1000 );
        VenueDTO dto2 = new VenueDTO(2L,  "Venue 2", "Test Location", 1000 );

        Mockito.when(venueService.findAll()).thenReturn(Flux.just(dto1, dto2));

        // Test GET /venue/all
        webTestClient.get()
                .uri("/venue/all")
                .exchange() // Send request
                .expectStatus().isOk() // Check return status
                .expectBodyList(VenueDTO.class)
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void findById_ReturnsMonoOfVenue() throws Exception {
        //DTO to be used as mock
        VenueDTO dto = new VenueDTO(1L,  "Venue 1", "Test Location", 1000 );

        Mockito.when(venueService.findById(1L)).thenReturn(Mono.just(dto));

        // Test GET /venue/id/1
        webTestClient.get()
                .uri("/venue/id/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(VenueDTO.class)
                .isEqualTo(dto);

        Mockito.verify(venueService).findById(eq(1L));
    }

    @Test
    void save_ReturnsSavedVenue() {
        // DTO to be used as mock and request
        VenueDTO dtoToSave = new VenueDTO(null,  "Venue 1", "Test Location", 1000 );
        VenueDTO savedDto = new VenueDTO(10L,   "Venue 1", "Test Location", 1000 );

        Mockito.when(venueService.save(any(VenueDTO.class))).thenReturn(Mono.just(savedDto));

        // Test POST /venue, enviando JSON
        webTestClient.post()
                .uri("/venue")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dtoToSave)
                .exchange()
                .expectStatus().isOk()
                .expectBody(VenueDTO.class)
                .isEqualTo(savedDto);

        Mockito.verify(venueService).save(dtoToSave);
    }

    @Test
    void deleteById_ReturnsOk() {
        // Mock delete endpoint
        Mockito.when(venueService.delete(1L)).thenReturn(Mono.empty());

        // Test delete endpoint
        webTestClient.delete()
                .uri("/venue/id/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);

        Mockito.verify(venueService).delete(1L);
    }
}