package com.example.demo.dto.artist;

import com.example.demo.entity.Artist;
import lombok.Builder;
import lombok.Data;

@Data
public class ArtistInfoDto {

    private String name;

    private String profile;

    private String intro;

    @Builder
    public ArtistInfoDto(Artist artist){
        this.name = artist.getName();
        this.profile = artist.getProfile();
        this.intro = artist.getIntro();
    }

    public void update(String name, String profile, String intro){
        this.name = name;
        this.profile = profile;
        this.intro = intro;
    }
}
