package com.connections.external.musicbrainz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MusicBrainzArtist {

    private String id;

    private String type;

    @JsonProperty("type-id")
    private String typeId;

    private Integer score;

    private String name;

    @JsonProperty("sort-name")
    private String sortName;

    private String country;

    private MusicBrainzArea area;

    @JsonProperty("begin-area")
    private MusicBrainzArea beginArea;

    private List<String> isnis;

    private MusicBrainzLifeSpan lifeSpan;

    private List<MusicBrainzAlias> aliases;

    private List<MusicBrainzTag> tags;

    private String disambiguation;
}