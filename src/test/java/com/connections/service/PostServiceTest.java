package com.connections.service;

import com.connections.dto.CommentDTO;
import com.connections.dto.PostDTO;
import com.connections.entity.PostEntity;
import com.connections.repository.PostRepository;
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
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    // Service Mock
    @Mock
    private CommentService commentService;

    @InjectMocks
    private PostService postService;

    private PostEntity entity1;
    private PostEntity entity2;

    private PostDTO dto1;
    private PostDTO dto2;

    @BeforeEach
    void setup() {
        entity1 = new PostEntity(1L, "Content 1", LocalDateTime.of(2025,1,18,11,46,31,496193900), "Test CreatedBy");
        entity2 = new PostEntity(2L, "Content 2", LocalDateTime.of(2025,1,18,11,46,31,496193900), "Test CreatedBy");

        dto1 = new PostDTO(1L, "Content 1", LocalDateTime.of(2025,1,18,11,46,31,496193900), "Test CreatedBy");
        dto2 = new PostDTO(2L, "Content 2", LocalDateTime.of(2025,1,18,11,46,31,496193900), "Test CreatedBy");
    }

    @Test
    void findAll_ShouldReturnFluxOfPostDTO() {
        // Mock Repository call
        Mockito.when(postRepository.findAll()).thenReturn(Flux.just(entity1, entity2));

        // Service Call
        Flux<PostDTO> result = postService.findAll();

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(dto1)
                .expectNext(dto2)
                .verifyComplete();

        // Check if repository.findAll() was called
        Mockito.verify(postRepository).findAll();
    }

    @Test
    void findById_ShouldReturnMonoOfPostDTO() {
        // Mock Repository call
        Mockito.when(postRepository.findById(1L)).thenReturn(Mono.just(entity1));

        // Service Call
        Mono<PostDTO> result = postService.findById(1L);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(dto1)
                .verifyComplete();

        // Check if repository.findById() was called
        Mockito.verify(postRepository).findById(eq(1L));
    }

    @Test
    void findById_ShouldReturnMonoOfPostDTOWithComments() {
        //DTOs for service call
        CommentDTO commentDto1 = new CommentDTO(1L, "Comment One", 1L, null, LocalDateTime.now(), "Test");
        CommentDTO commentDto2 = new CommentDTO(2L, "Comment Two", 1L, null, LocalDateTime.now(), "Test");
        dto1.setComments(Arrays.asList(commentDto1, commentDto2));

        //Mock service call
        Mockito.when(commentService.findByPostId(1L)).thenReturn(Flux.just(commentDto1, commentDto2));

        // Mock Repository call
        Mockito.when(postRepository.findById(1L)).thenReturn(Mono.just(entity1));

        // Service Call
        Mono<PostDTO> result = postService.findPostWithComments(1L);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(dto1)
                .verifyComplete();

        // Check if repository.findById() was called
        Mockito.verify(postRepository).findById(eq(1L));
    }
/*
*
* */
    @Test
    void save_ShouldReturnSavedPostDTO() {
        // Objects to use as Mock
        PostEntity savedEntity   = new PostEntity(3L, "Content 1", LocalDateTime.now(), "Test CreatedBy");
        PostDTO dtoToSave = new PostDTO(null, "Content 1", LocalDateTime.now(), "Test CreatedBy");
        PostDTO expectedDTO = new PostDTO(3L, "Content 1", LocalDateTime.now(), "Test CreatedBy");

        //Mock Repository call
        Mockito.when(postRepository.save(any(PostEntity.class)))
                .thenReturn(Mono.just(savedEntity));

        //Service Call
        Mono<PostDTO> result = postService.save(dtoToSave);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(expectedDTO)
                .verifyComplete();

        // Check if save was called
        Mockito.verify(postRepository).save(any(PostEntity.class));
    }

    @Test
    void delete_ShouldCallRepositoryAndReturnMonoVoid() {
        //Mock Repository call
        Mockito.when(postRepository.deleteById(1L)).thenReturn(Mono.empty());

        //Service Call
        Mono<Void> result = postService.delete(1L);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .verifyComplete();

        // Check if delete was called
        Mockito.verify(postRepository).deleteById(eq(1L));
    }
}