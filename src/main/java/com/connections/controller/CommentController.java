package com.connections.controller;

import com.connections.dto.CommentDTO;
import com.connections.dto.PostDTO;
import com.connections.service.CommentService;
import com.connections.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.stream.events.Comment;

@RestController
@RequestMapping("/comment")
public class CommentController {

    final CommentService service;

    public CommentController(final CommentService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Flux<CommentDTO> findAll() throws Exception {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    public Mono<CommentDTO> findById(@PathVariable("id") final Long id) throws Exception {
        return service.findById(id);
    }

    @PostMapping
    public Mono<CommentDTO> save(@RequestBody final CommentDTO dto){
        return service.save(dto);
    }

    @DeleteMapping("/id/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") final Long id){
        return this.service.delete(id)
                .map(ResponseEntity::ok);
    }

}