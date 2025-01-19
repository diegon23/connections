package com.connections.service.impl;

import com.connections.dto.LinkDTO;
import com.connections.entity.LinkEntity;
import com.connections.repository.LinkRepository;
import com.connections.service.ILinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of {@link ILinkService}.
 * <p>For method details, see the interface documentation.</p>
 */
@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements ILinkService {

    private final LinkRepository linkRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Flux<LinkDTO> findAll() {
        return linkRepository.findAll()
                .map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<LinkDTO> findById(Long linkId) {
        return linkRepository.findById(linkId)
                .map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<LinkDTO> save(LinkDTO link) {
        return linkRepository
                .save(this.toEntity(link))
                .map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<Void> delete(Long linkId) {
        return linkRepository.deleteById(linkId);
    }

    /**
     * Converts LinkEntity to LinkDTO.
     */
    private LinkDTO toDto(LinkEntity entity) {
        return new LinkDTO(
                entity.getId(),
                entity.getLink(),
                entity.getCreatorId(),
                entity.getDescription()
        );
    }

    /**
     * Converts LinkDTO to LinkEntity.
     */
    private LinkEntity toEntity(LinkDTO dto) {
        return new LinkEntity(
                dto.getId(),
                dto.getLink(),
                dto.getCreatorId(),
                dto.getDescription()
        );
    }
}
