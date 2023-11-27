package com.example.demo.dto.artist;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class CommentDto {
    private Long commentId;
    private String createdAt;
    private String updatedAt;
    private String content;
    private String writer;

    @Builder
    public CommentDto(Long commentId, String createdAt, String updatedAt, String content, String writer) {
        this.commentId = commentId;
        this.createdAt  = createdAt;
        this.updatedAt = updatedAt;
        this.content = content;
        this.writer = writer;
    }
}
