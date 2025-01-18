package com.connections.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RsvpDTO {
    private Long id;

    private Long eventId;

    private Long creatorId;

    private String status;

    private LocalDateTime lastUpdated;
}