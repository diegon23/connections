package com.connections.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("link")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkEntity {
    @Id
    private Long id;

    @Column("creator_id")
    private Long creatorId;

    private String description;
}