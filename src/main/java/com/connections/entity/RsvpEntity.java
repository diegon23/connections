package com.connections.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("rsvp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RsvpEntity {
    @Id
    private Long id;

    @Column("event_id")
    private Long eventId;

    @Column("creator_id")
    private Long creatorId;

    private String status;

    @Column("last_updated")
    private LocalDateTime lastUpdated;
}