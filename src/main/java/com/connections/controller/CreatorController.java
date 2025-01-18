package com.connections.controller;

import com.connections.dto.CreatorDTO;
import com.connections.service.CreatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/creator")
public class CreatorController {

    final CreatorService service;

    public CreatorController(final CreatorService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Flux<CreatorDTO> findAll() throws Exception {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    public Mono<CreatorDTO> findById(@PathVariable("id") final Long id) throws Exception {
        return service.findById(id);
    }

    @PostMapping
    public Mono<CreatorDTO> save(@RequestBody final CreatorDTO dto){
        return service.save(dto);
    }

    @DeleteMapping("/id/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") final Long id){
        return this.service.delete(id)
                .map(ResponseEntity::ok);
    }

}