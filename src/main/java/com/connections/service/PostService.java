package com.connections.service;

import com.connections.dto.CommentDTO;
import com.connections.dto.PostDTO;
import com.connections.entity.PostEntity;
import com.connections.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentService commentService;

    public Mono<PostDTO> findPostWithComments(Long postId) {
        // Find post by id
        Mono<PostDTO> postMono = this.findById(postId);

        // Find comments for the post
        Flux<CommentDTO> commentFlux = commentService.findByPostId(postId);

        // Zip both and return post with comments
        return postMono
                .zipWith(commentFlux.collectList())
                .map(tuple -> {
                    PostDTO post = tuple.getT1();
                    var comments = tuple.getT2();
                    return new PostDTO(
                            post.getId(),
                            post.getContent(),
                            post.getCreatedAt(),
                            post.getCreatedBy(),
                            comments
                    );
                });
    }

    public Flux<PostDTO> findAll(){
        return postRepository.findAll().map(this::toDto);
    }

    public Mono<PostDTO> findById(Long postId){
        return postRepository.findById(postId)
                .map(this::toDto);
    }

    public Mono<PostDTO> save(PostDTO dto){
        return postRepository
                .save(this.toEntity(dto))
                .map(this::toDto);
    }

    public Mono<Void> delete(Long postId){
        return postRepository.deleteById(postId);
    }

    private PostDTO toDto(PostEntity entity){
        return new PostDTO(entity.getId(), entity.getContent(), entity.getCreatedAt(), entity.getCreatedBy());
    }

    private PostEntity toEntity(PostDTO dto){
        return new PostEntity(dto.getId(), dto.getContent(), dto.getCreatedAt(), dto.getCreatedBy());
    }

}