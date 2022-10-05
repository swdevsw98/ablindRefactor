package com.example.demo.entity.artist;

import com.example.demo.entity.BaseEntity;
import com.example.demo.entity.Member;
import com.example.demo.entity.artist.Artist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Data
@Table(name = "follow")
@Entity
public class Follow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_artist_id")
    private Artist followArtistId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_user_id")
    private Member followUserId;

    private Long price;

    //승인 여부
    private boolean approve;

    public Follow (Artist artist, Member member, Long price){
        this.followArtistId = artist;
        this.followUserId = member;
        this.price = price;
    }

}
