package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

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

    private String name;

    private String profile;

    private String intro;

    public Artist (Long artist_id, String name, String profile, String intro){
        this.artistId = artist_id;
        this.name = name;
        this.profile = profile;
        this.intro = intro;
    }
}
