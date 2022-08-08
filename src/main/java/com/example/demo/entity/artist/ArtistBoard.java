package com.example.demo.entity.artist;

import com.example.demo.dto.artist.ArtistBoardDto;
import com.example.demo.entity.BaseEntity;
import com.example.demo.entity.artist.Artist;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@ToString
@Data
@Table(name = "artist_board")
@Entity
public class ArtistBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artistId;

    private String title;

    private String content;

    private String email;

    private String writer;

    @OneToMany(mappedBy = "artistBoard", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public ArtistBoard(Artist artist, ArtistBoardDto artistBoardDto){
        this.artistId = artist;
        this.title = artistBoardDto.getTitle();
        this.content = artistBoardDto.getContent();
        this.email = artistBoardDto.getEmail();
        this.writer = artistBoardDto.getWriter();
    }
}
