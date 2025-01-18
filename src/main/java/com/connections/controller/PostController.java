package com.connections.controller;

import com.connections.dto.PostDTO;
import com.connections.dto.VenueDTO;
import com.connections.service.PostService;
import com.connections.service.VenueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/post")
public class PostController {

    final PostService service;

    public PostController(final PostService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Flux<PostDTO> findAll() throws Exception {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    public Mono<PostDTO> findById(@PathVariable("id") final Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping("/id/{id}/comments")
    public Mono<PostDTO> findWithCommentsById(@PathVariable("id") final Long id) throws Exception {
        return service.findPostWithComments(id);
    }

    @PostMapping
    public Mono<PostDTO> save(@RequestBody final PostDTO dto){
        return service.save(dto);
    }

    @DeleteMapping("/id/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") final Long id){
        return this.service.delete(id)
                .map(ResponseEntity::ok);
    }

}