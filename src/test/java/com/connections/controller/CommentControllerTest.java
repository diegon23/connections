package com.connections.controller;

import com.connections.dto.CommentDTO;
import com.connections.dto.CommentDTO;
import com.connections.service.CommentService;
import com.connections.service.CommentService;
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

@WebFluxTest(CommentController.class)
class CommentControllerTest {

    // Client for test requests
    @Autowired
    private WebTestClient webTestClient;

    // Service Mock
    @MockBean
    private CommentService commentService;

    //
    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll_ReturnsFluxOfComments() throws Exception {
        // DTOs to be used as mock
        CommentDTO dto1 = new CommentDTO(1L, "Comment One", 1L, null, LocalDateTime.now(), "Test");
        CommentDTO dto2 = new CommentDTO(2L, "Comment Two", 1L, null, LocalDateTime.now(), "Test");

        Mockito.when(commentService.findAll()).thenReturn(Flux.just(dto1, dto2));

        // Test GET /comment/all
        webTestClient.get()
                .uri("/comment/all")
                .exchange() // Send request
                .expectStatus().isOk() // Check return status
                .expectBodyList(CommentDTO.class)
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void findById_ReturnsMonoOfComment() throws Exception {
        //DTO to be used as mock
        CommentDTO dto = new CommentDTO(1L, "Comment One", 1L, null, LocalDateTime.now(), "Test");

        Mockito.when(commentService.findById(1L)).thenReturn(Mono.just(dto));

        // Test GET /comment/id/1
        webTestClient.get()
                .uri("/comment/id/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommentDTO.class)
                .isEqualTo(dto);

        Mockito.verify(commentService).findById(eq(1L));
    }

    @Test
    void save_ReturnsSavedComment() {
        // DTO to be used as mock and request
        CommentDTO dtoToSave = new CommentDTO(null, "Comment One", 1L, null, LocalDateTime.now(), "Test");
        CommentDTO savedDto = new CommentDTO(10L, "Comment One", 1L, null, LocalDateTime.now(), "Test");

        Mockito.when(commentService.save(any(CommentDTO.class))).thenReturn(Mono.just(savedDto));

        // Test POST /comment, enviando JSON
        webTestClient.post()
                .uri("/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dtoToSave)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommentDTO.class)
                .isEqualTo(savedDto);

        Mockito.verify(commentService).save(dtoToSave);
    }

    @Test
    void deleteById_ReturnsOk() {
        // Mock delete endpoint
        Mockito.when(commentService.delete(1L)).thenReturn(Mono.empty());

        // Test delete endpoint
        webTestClient.delete()
                .uri("/comment/id/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);

        Mockito.verify(commentService).delete(1L);
    }
}