package com.connections.controller;

import com.connections.dto.LinkDTO;
import com.connections.service.LinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/link")
public class LinkController {

    final LinkService service;

    public LinkController(final LinkService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Flux<LinkDTO> findAll() throws Exception {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    public Mono<LinkDTO> findById(@PathVariable("id") final Long id) throws Exception {
        return service.findById(id);
    }

    @PostMapping
    public Mono<LinkDTO> save(@RequestBody final LinkDTO dto){
        return service.save(dto);
    }

    @DeleteMapping("/id/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") final Long id){
        return this.service.delete(id)
                .map(ResponseEntity::ok);
    }

}