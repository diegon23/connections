package com.connections.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(ExternalClientsProperties.class)
public class ExternalClientsConfig {

    private final ExternalClientsProperties props;

    public ExternalClientsConfig(ExternalClientsProperties props) {
        this.props = props;
    }

    @Bean
    public WebClient musicBrainzWebClient() {
        return WebClient.builder()
                .baseUrl(props.getMusicbrainz().getUrl())
                .defaultHeader("User-Agent", props.getMusicbrainz().getUserAgent())
                .build();
    }
 }