package com.example.demo.dto;

import com.example.demo.entity.ArtistBoard;
import lombok.Data;

@Data
public class ArtistBoardDto {

    private String title;

    private String content;

    public ArtistBoardDto(ArtistBoard artistBoard){
        this.title = artistBoard.getTitle();
        this.content = artistBoard.getContent();
    }
}
