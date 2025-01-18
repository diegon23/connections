package com.connections.service;

import com.connections.dto.VenueDTO;
import com.connections.repository.VenueRepository;
import com.connections.entity.VenueEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepository;

    public Flux<VenueDTO> findAll(){
        return venueRepository.findAll().map(this::toDto);
    }

    public Mono<VenueDTO> findById(Long venueId){
        return venueRepository.findById(venueId)
                .map(this::toDto);
    }

    public Mono<VenueDTO> save(VenueDTO venue){
        return venueRepository
                .save(this.toEntity(venue))
                .map(this::toDto);
    }

    public Mono<Void> delete(Long venueId){
        return venueRepository.deleteById(venueId);
    }

    private VenueDTO toDto(VenueEntity entity){
        return new VenueDTO(entity.getId(), entity.getName(), entity.getLocation(), entity.getCapacity());
    }

    private VenueEntity toEntity(VenueDTO dto){
        return new VenueEntity(dto.getId(), dto.getName(), dto.getLocation(), dto.getCapacity());
    }
}