package com.example.demo.dto;

import com.example.demo.entity.ArtistBoard;
import lombok.Builder;
import lombok.Data;

@Data
public class ArtistBoardDto {

    private String title;

    private String content;

    @Builder
    public ArtistBoardDto(String title, String content){
        this.title = title;
        this.content = content;
    }
    public ArtistBoardDto(ArtistBoard artistBoard){
        this.title = artistBoard.getTitle();
        this.content = artistBoard.getContent();
    }
}
