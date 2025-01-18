package com.connections.service;

import com.connections.dto.LinkDTO;
import com.connections.repository.LinkRepository;
import com.connections.entity.LinkEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    public Flux<LinkDTO> findAll(){
        return linkRepository.findAll().map(this::toDto);
    }

    public Mono<LinkDTO> findById(Long linkId){
        return linkRepository.findById(linkId)
                .map(this::toDto);
    }

    public Mono<LinkDTO> save(LinkDTO link){
        return linkRepository
                .save(this.toEntity(link))
                .map(this::toDto);
    }

    public Mono<Void> delete(Long linkId){
        return linkRepository.deleteById(linkId);
    }

    private LinkDTO toDto(LinkEntity entity){
        return new LinkDTO(entity.getId(), entity.getCreatorId(), entity.getDescription());
    }

    private LinkEntity toEntity(LinkDTO dto){
        return new LinkEntity(dto.getId(), dto.getCreatorId(), dto.getDescription());
    }
}