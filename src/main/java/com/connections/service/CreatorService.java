package com.connections.service;

import com.connections.dto.CreatorDTO;
import com.connections.entity.CreatorEntity;
import com.connections.repository.CreatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreatorService {

    private final CreatorRepository creatorRepository;

    public Flux<CreatorDTO> findAll(){
        return creatorRepository.findAll().map(this::toDto);
    }

    public Mono<CreatorDTO> findById(Long creatorId){
        return creatorRepository.findById(creatorId)
                .map(this::toDto);
    }

    public Mono<CreatorDTO> save(CreatorDTO creator){
        return creatorRepository
                .save(this.toEntity(creator))
                .map(this::toDto);
    }

    public Mono<Void> delete(Long creatorId){
        return creatorRepository.deleteById(creatorId);
    }

    private CreatorDTO toDto(CreatorEntity entity){
        return new CreatorDTO(entity.getId(), entity.getName(), entity.getPronouns(), entity.getBio());
    }

    private CreatorEntity toEntity(CreatorDTO dto){
        return new CreatorEntity(dto.getId(), dto.getName(), dto.getPronouns(), dto.getBio());
    }
}