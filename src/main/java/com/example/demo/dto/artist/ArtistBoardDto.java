package com.example.demo.dto.artist;

import com.example.demo.entity.ArtistBoard;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ArtistBoardDto {

    private Long boardId;

    private String title;

    private String content;

    @Builder
    public ArtistBoardDto(Long boardId, String title, String content){
        this.boardId = boardId;
        this.title = title;
        this.content = content;
    }

    public ArtistBoardDto(String title, String content){
        this.title = title;
        this.content = content;
    }

    public ArtistBoardDto(ArtistBoard artistBoard){
        this.title = artistBoard.getTitle();
        this.content = artistBoard.getContent();
    }
}
