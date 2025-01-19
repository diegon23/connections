package com.connections.external.musicbrainz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MusicBrainzAlias {

    @JsonProperty("sort-name")
    private String sortName;

    @JsonProperty("type-id")
    private String typeId;

    private String name;

    private String locale;

    private String type;

    private Boolean primary;

    @JsonProperty("begin-date")
    private String beginDate;

    @JsonProperty("end-date")
    private String endDate;

}
