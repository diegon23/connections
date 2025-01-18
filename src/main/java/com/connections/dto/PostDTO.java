package com.connections.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    private String createdBy;

    private List<CommentDTO> comments;

    public PostDTO(Long id, String content, LocalDateTime createdAt, String createdBy) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
}