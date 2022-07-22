package com.example.demo.entity.artist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Data
@NoArgsConstructor
@Table(name = "artist")
@Entity
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "artist_id", unique = true)
    private Long artistId;

    private String profile;

    private String backGround;
    private String name;
    private String intro;

    private String subTitle;

    private String detail;

    private String youtube;

    private String content;


    @JsonIgnore
    @OneToMany(mappedBy = "artistWorkId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArtWorks> works = new ArrayList<>();

    @OneToMany(mappedBy = "artistId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArtistBoard> artistBoards = new ArrayList<>();

    @OneToMany(mappedBy = "followArtistId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> userFollow = new ArrayList<>();

    public Artist (Long artist_id, String name, String profile, String intro){
        this.artistId = artist_id;
        this.name = name;
        this.profile = profile;
        this.intro = intro;
    }
}
