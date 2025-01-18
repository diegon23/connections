package com.connections.controller;

import com.connections.dto.RsvpDTO;
import com.connections.service.RsvpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/rsvp")
public class RsvpController {

    final RsvpService service;

    public RsvpController(final RsvpService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Flux<RsvpDTO> findAll() throws Exception {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    public Mono<RsvpDTO> findById(@PathVariable("id") final Long id) throws Exception {
        return service.findById(id);
    }

    @PostMapping
    public Mono<RsvpDTO> save(@RequestBody final RsvpDTO dto){
        return service.save(dto);
    }

    @DeleteMapping("/id/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") final Long id){
        return this.service.delete(id)
                .map(ResponseEntity::ok);
    }

}