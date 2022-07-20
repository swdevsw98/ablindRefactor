package com.example.demo.entity;

import com.example.demo.dto.ArtistBoardDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@ToString
@Data
@Table(name = "artist_board")
@Entity
public class ArtistBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artistId;

    private String title;

    private String content;

    public ArtistBoard(Artist artist, ArtistBoardDto artistBoardDto){
        this.artistId = artist;
        this.title = artistBoardDto.getTitle();
        this.content = artistBoardDto.getContent();
    }
}
