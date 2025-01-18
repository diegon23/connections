package com.connections.controller;

import com.connections.dto.EventDTO;
import com.connections.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/event")
public class EventController {

    final EventService service;

    public EventController(final EventService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Flux<EventDTO> findAll() throws Exception {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    public Mono<EventDTO> findById(@PathVariable("id") final Long id) throws Exception {
        return service.findById(id);
    }

    @PostMapping
    public Mono<EventDTO> save(@RequestBody final EventDTO dto){
        return service.save(dto);
    }

    @DeleteMapping("/id/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") final Long id){
        return this.service.delete(id)
                .map(ResponseEntity::ok);
    }

}