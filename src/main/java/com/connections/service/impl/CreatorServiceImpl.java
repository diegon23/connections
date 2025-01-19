package com.connections.service.impl;

import com.connections.dto.CreatorDTO;
import com.connections.dto.InspirationDTO;
import com.connections.entity.CreatorEntity;
import com.connections.entity.InspirationEntity;
import com.connections.external.musicbrainz.dto.MusicBrainzArtist;
import com.connections.repository.CreatorRepository;
import com.connections.repository.InspirationRepository;
import com.connections.service.ICreatorService;
import com.connections.service.IInspirationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collector;

/**
 * Implementation of {@link ICreatorService}.
 * <p>For method details, see the interface documentation.</p>
 */
@Service
@RequiredArgsConstructor
public class CreatorServiceImpl implements ICreatorService {

    private final CreatorRepository creatorRepository;

    private final InspirationRepository inspirationRepository;

    private final IInspirationService inspirationService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Flux<CreatorDTO> findAll() {
        return creatorRepository.findAll().map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<CreatorDTO> findById(Long creatorId) {
        return creatorRepository.findById(creatorId)
                .map(this::toDto)
                .flatMap(this::getCreatorWithInspirations);
    }


    /**
     * Method to find all inspirations by creatorId
     */
    private Mono<CreatorDTO> getCreatorWithInspirations(CreatorDTO creatorDTO){
        Flux<InspirationDTO> inspirations = inspirationRepository.findByCreatorId(creatorDTO.getId()).map(this::toInspirationDto);

        return inspirations.map(inspiration -> this.addInspirationToCreatorDTO(creatorDTO, inspiration)).then(Mono.just(creatorDTO));
    }



    /**
     * Method to find map inspirations to CreatorDTO
     */
    private CreatorDTO addInspirationToCreatorDTO(final CreatorDTO creatorDTO,
                                                  final InspirationDTO inspirationDTO){
        if(CollectionUtils.isEmpty(creatorDTO.getInspirations())){
            creatorDTO.setInspirations(List.of(inspirationDTO));
        } else {
            creatorDTO.getInspirations().add(inspirationDTO);
        }

        return creatorDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<CreatorDTO> save(CreatorDTO creator) {
        return creatorRepository
                .save(this.toEntity(creator))
                .map(this::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<Void> delete(Long creatorId) {
        return creatorRepository.deleteById(creatorId);
    }

    /**
     * {@inheritDoc}
     */
    public Mono<InspirationDTO> addInspirationToCreator(Long creatorId, String inspirationId){
        Mono<CreatorDTO> creator = this.findById(creatorId);
        Mono<MusicBrainzArtist> inspiration = inspirationService.findArtistById(inspirationId);

        return creator.zipWith(inspiration)
                .flatMap(tuple -> this.addInspiration(tuple.getT1(), tuple.getT2()));
    }

    /**
     * Add inspiration to CreatorDTO.
     */
    private Mono<InspirationDTO> addInspiration(CreatorDTO creator,
                                                MusicBrainzArtist artist) {
        if(artist != null &&
                StringUtils.isNotEmpty(artist.getName()) &&
                creator != null &&
                creator.getId() != null) {
            InspirationEntity inspirationEntity = new InspirationEntity();
            inspirationEntity.setInspirationName(artist.getName());
            inspirationEntity.setCreatorId(creator.getId());
            return inspirationRepository.save(inspirationEntity).map(this::toInspirationDto);
        }

        return Mono.empty();
    }

    /**
     * Converts CreatorEntity to CreatorDTO.
     */
    private CreatorDTO toDto(CreatorEntity entity) {
        return new CreatorDTO(
                entity.getId(),
                entity.getName(),
                entity.getPronouns(),
                entity.getBio()
        );
    }

    /**
     * Converts CreatorEntity to CreatorDTO.
     */
    private InspirationDTO toInspirationDto(InspirationEntity entity) {
        return new InspirationDTO(
                entity.getId(),
                entity.getInspirationName()
        );
    }

    /**
     * Converts CreatorDTO to CreatorEntity.
     */
    private CreatorEntity toEntity(CreatorDTO dto) {
        return new CreatorEntity(
                dto.getId(),
                dto.getName(),
                dto.getPronouns(),
                dto.getBio()
        );
    }
}
