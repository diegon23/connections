package com.connections.service;

import com.connections.dto.RsvpDTO;
import com.connections.repository.RsvpRepository;
import com.connections.entity.RsvpEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RsvpService {

    private final RsvpRepository rsvpRepository;

    public Flux<RsvpDTO> findAll(){
        return rsvpRepository.findAll().map(this::toDto);
    }

    public Mono<RsvpDTO> findById(Long rsvpId){
        return rsvpRepository.findById(rsvpId)
                .map(this::toDto);
    }

    public Mono<RsvpDTO> save(RsvpDTO rsvp){
        return rsvpRepository
                .save(this.toEntity(rsvp))
                .map(this::toDto);
    }

    public Mono<Void> delete(Long rsvpId){
        return rsvpRepository.deleteById(rsvpId);
    }

    private RsvpDTO toDto(RsvpEntity entity){
        return new RsvpDTO(entity.getId(), entity.getEventId(), entity.getCreatorId(), entity.getStatus(), entity.getLastUpdated());
    }

    private RsvpEntity toEntity(RsvpDTO dto){
        return new RsvpEntity(dto.getId(), dto.getEventId(), dto.getCreatorId(), dto.getStatus(), dto.getLastUpdated());
    }
}