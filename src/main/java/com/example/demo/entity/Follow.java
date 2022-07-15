package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Data
@Table(name = "follow")
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_artist_id")
    private Artist followArtistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_user_id")
    private Member followUserId;

    public Follow (Artist artist, Member member){
        this.followArtistId = artist;
        this.followUserId = member;
    }

}
