package com.connections.external.musicbrainz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MusicBrainzArea {

    private String id;

    private String type;

    @JsonProperty("type-id")
    private String typeId;

    private String name;

    @JsonProperty("sort-name")
    private String sortName;

    @JsonProperty("life-span")
    private MusicBrainzLifeSpan lifeSpan;
}