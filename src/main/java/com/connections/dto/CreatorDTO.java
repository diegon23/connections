package com.connections.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatorDTO {

    public CreatorDTO(Long id, String name, String pronouns, String bio) {
        this.id = id;
        this.name = name;
        this.pronouns = pronouns;
        this.bio = bio;
    }

    private Long id;

    private String name;

    private String pronouns;

    private String bio;

    private List<InspirationDTO> inspirations;
}