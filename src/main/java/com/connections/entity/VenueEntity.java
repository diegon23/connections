package com.connections.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("venue")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueEntity {
    @Id
    private Long id;

    private String name;

    private String location;

    private int capacity;
}