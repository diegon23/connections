package com.connections.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("event")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {
    @Id
    private Long id;

    private String title;

    private String description;

    @Column("date_time")
    private LocalDateTime dateTime;

    @Column("venue_id")
    private Long venueId;
}