package com.connections.controller;

import com.connections.dto.CommentDTO;
import com.connections.dto.PostDTO;
import com.connections.service.IPostService;
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
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@WebFluxTest(PostController.class)
class PostControllerTest {

    // Client for test requests
    @Autowired
    private WebTestClient webTestClient;

    // Service Mock
    @MockBean
    private IPostService postService;

    //
    @BeforeEach
    void setUp() {

    }

    @Test
    void findAll_ReturnsFluxOfPosts() throws Exception {
        // DTOs to be used as mock
        PostDTO dto1 = new PostDTO(1L, "Content 1", LocalDateTime.of(2025,1,18,11,46,31,496193900), "Test CreatedBy");
        PostDTO dto2 = new PostDTO(2L,  "Content 2", LocalDateTime.of(2025,1,18,11,46,31,496193900), "Test CreatedBy");

        Mockito.when(postService.findAll()).thenReturn(Flux.just(dto1, dto2));

        // Test GET /post/all
        webTestClient.get()
                .uri("/post/all")
                .exchange() // Send request
                .expectStatus().isOk() // Check return status
                .expectBodyList(PostDTO.class)
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void findById_ReturnsMonoOfPost() throws Exception {
        //DTO to be used as mock
        PostDTO dto = new PostDTO(1L,  "Content 1", LocalDateTime.of(2025,1,18,11,46,31,496193900), "Test CreatedBy");

        Mockito.when(postService.findById(1L)).thenReturn(Mono.just(dto));

        // Test GET /post/id/1
        webTestClient.get()
                .uri("/post/id/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PostDTO.class)
                .isEqualTo(dto);

        Mockito.verify(postService).findById(eq(1L));
    }

    @Test
    void findById_ReturnsMonoOfPostWithComments() throws Exception {
        //DTOs to be used as mock
        PostDTO postDTO = new PostDTO(1L,  "Content 1", LocalDateTime.of(2025,1,18,11,46,31,496193900), "Test CreatedBy");
        CommentDTO commentDto1 = new CommentDTO(1L, "Comment One", 1L, null, LocalDateTime.of(2025,1,18,11,46,31,496193900), "Test");
        CommentDTO commentDto2 = new CommentDTO(2L, "Comment Two", 1L, null, LocalDateTime.of(2025,1,18,11,46,31,496193900), "Test");
        postDTO.setComments(Arrays.asList(commentDto1, commentDto2));

        Mockito.when(postService.findPostWithComments(1L)).thenReturn(Mono.just(postDTO));

        // Test GET /post/id/1
        webTestClient.get()
                .uri("/post/id/1/comments")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PostDTO.class)
                .isEqualTo(postDTO);

        Mockito.verify(postService).findPostWithComments(eq(1L));
    }

    @Test
    void save_ReturnsSavedPost() {
        // DTO to be used as mock and request
        PostDTO dtoToSave = new PostDTO(null,  "Content 1", LocalDateTime.of(2025,1,18,11,46,31,496193900), "Test CreatedBy");
        PostDTO savedDto = new PostDTO(10L,   "Content 1", LocalDateTime.of(2025,1,18,11,46,31,496193900), "Test CreatedBy");

        Mockito.when(postService.save(any(PostDTO.class))).thenReturn(Mono.just(savedDto));

        // Test POST /post, enviando JSON
        webTestClient.post()
                .uri("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dtoToSave)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PostDTO.class)
                .isEqualTo(savedDto);

        Mockito.verify(postService).save(dtoToSave);
    }

    @Test
    void deleteById_ReturnsOk() {
        // Mock delete endpoint
        Mockito.when(postService.delete(1L)).thenReturn(Mono.empty());

        // Test delete endpoint
        webTestClient.delete()
                .uri("/post/id/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);

        Mockito.verify(postService).delete(1L);
    }
}