package com.example.demo.entity.artist;

import com.example.demo.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Table(name = "artist_board_comment")
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;
    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    private ArtistBoard artistBoard;

    public Comment(String content, String name){
        this.content = content;
        this.writer = name;
    }

    //==연관 관계 메소드==//
    public void setBoard(ArtistBoard artistBoard){
        this.artistBoard = artistBoard;
        artistBoard.getComments().add(this);
    }

    //==수정 메소드==//
    public void updateComment(String content){
        this.content = content;
    }
}
