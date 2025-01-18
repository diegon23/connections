package com.connections.service;

import com.connections.dto.CommentDTO;
import com.connections.entity.CommentEntity;
import com.connections.repository.CommentRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private CommentEntity entity1;
    private CommentEntity entity2;

    private CommentDTO dto1;
    private CommentDTO dto2;

    @BeforeEach
    void setup() {
        entity1 = new CommentEntity(1L, "Comment One", 1L, null, LocalDateTime.now(), "Test");
        entity2 = new CommentEntity(2L, "Comment Two", 1L, null, LocalDateTime.now(), "Test");

        dto1 = new CommentDTO(1L, "Comment One", 1L, null, LocalDateTime.now(), "Test");
        dto2 = new CommentDTO(2L, "Comment Two", 1L, null, LocalDateTime.now(), "Test");
    }

    @Test
    void findAll_ShouldReturnFluxOfCommentDTO() {
        // Mock Repository call
        Mockito.when(commentRepository.findAll()).thenReturn(Flux.just(entity1, entity2));

        // Service Call
        Flux<CommentDTO> result = commentService.findAll();

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(dto1)
                .expectNext(dto2)
                .verifyComplete();

        // Check if repository.findAll() was called
        Mockito.verify(commentRepository).findAll();
    }

    @Test
    void findById_ShouldReturnMonoOfCommentDTO() {
        // Mock Repository call
        Mockito.when(commentRepository.findById(1L)).thenReturn(Mono.just(entity1));

        // Service Call
        Mono<CommentDTO> result = commentService.findById(1L);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(dto1)
                .verifyComplete();

        // Check if repository.findById() was called
        Mockito.verify(commentRepository).findById(eq(1L));
    }

    @Test
    void save_ShouldReturnSavedCommentDTO() {
        // Objects to use as Mock
        CommentEntity savedEntity = new CommentEntity(1L, "Comment One", 1L, null, LocalDateTime.now(), "Test");
        CommentDTO dtoToSave = new CommentDTO(null, "Comment One", 1L, null, LocalDateTime.now(), "Test");
        CommentDTO expectedDTO = new CommentDTO(1L, "Comment One", 1L, null, LocalDateTime.now(), "Test");

        //Mock Repository call
        Mockito.when(commentRepository.save(any(CommentEntity.class)))
                .thenReturn(Mono.just(savedEntity));

        //Service Call
        Mono<CommentDTO> result = commentService.save(dtoToSave);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .expectNext(expectedDTO)
                .verifyComplete();

        // Check if save was called
        Mockito.verify(commentRepository).save(any(CommentEntity.class));
    }

    @Test
    void delete_ShouldCallRepositoryAndReturnMonoVoid() {
        //Mock Repository call
        Mockito.when(commentRepository.deleteById(1L)).thenReturn(Mono.empty());

        //Service Call
        Mono<Void> result = commentService.delete(1L);

        // Validate with StepVerifier
        StepVerifier.create(result)
                .verifyComplete();

        // Check if delete was called
        Mockito.verify(commentRepository).deleteById(eq(1L));
    }
}