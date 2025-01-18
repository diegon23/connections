package com.connections.controller;

import com.connections.dto.VenueDTO;
import com.connections.service.VenueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/venue")
public class VenueController {

    final VenueService service;

    public VenueController(final VenueService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Flux<VenueDTO> findAll() throws Exception {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    public Mono<VenueDTO> findById(@PathVariable("id") final Long id) throws Exception {
        return service.findById(id);
    }

    @PostMapping
    public Mono<VenueDTO> save(@RequestBody final VenueDTO dto){
        return service.save(dto);
    }

    @DeleteMapping("/id/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") final Long id){
        return this.service.delete(id)
                .map(ResponseEntity::ok);
    }

}