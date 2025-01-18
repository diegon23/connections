package com.connections.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Table("creator")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatorEntity {
    @Id
    private Long id;

    private String name;

    private String pronouns;

    private String bio;
}