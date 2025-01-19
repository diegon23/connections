package com.connections.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "external")
public class ExternalClientsProperties {

    private MusicBrainzProperties musicbrainz;

    @Data
    public static class MusicBrainzProperties {
        private String url;
        private String userAgent;
    }
}