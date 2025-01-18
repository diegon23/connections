package com.connections.service;

import com.connections.dto.CommentDTO;
import com.connections.entity.CommentEntity;
import com.connections.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Flux<CommentDTO> findByPostId(Long postId){
        return commentRepository.findByPostId(postId)
                .map(this::toDto);
    }

    public Flux<CommentDTO> findAll(){
        return commentRepository.findAll().map(this::toDto);
    }

    public Mono<CommentDTO> findById(Long commentId){
        return commentRepository.findById(commentId)
                .map(this::toDto);
    }

    public Mono<CommentDTO> save(CommentDTO commentDto){
        return commentRepository
                .save(this.toEntity(commentDto))
                .map(this::toDto);
    }

    public Mono<Void> delete(Long commentId){
        return commentRepository.deleteById(commentId);
    }

    private CommentDTO toDto(CommentEntity entity){
        return new CommentDTO(entity.getId(), entity.getContent(), entity.getEventId(), entity.getPostId(), entity.getCreatedAt(), entity.getCreatedBy());
    }

    private CommentEntity toEntity(CommentDTO dto){
        return new CommentEntity(dto.getId(), dto.getContent(), dto.getEventId(), dto.getPostId(), dto.getCreatedAt(), dto.getCreatedBy());
    }
}