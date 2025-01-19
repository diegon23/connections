package com.connections.external.musicbrainz.dto;

import lombok.Data;

import java.util.List;

@Data
public class MusicBrainzSearchResult {

    private String created;
    private int count;
    private int offset;
    private List<MusicBrainzArtist> artists;

}