package com.example.demo.dto.artist;

import com.example.demo.entity.artist.ArtWorks;
import com.example.demo.entity.artist.Artist;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ArtistDetailDto {
    private Long artistId;

    private String profile;

    private String backGround;

    private String name;

    private String intro;

    private String subTitle;

    private String detail;

    private String youtube;

    private String content;

    private List<ArtWorks> works = new ArrayList<>();

    @Builder
    public  ArtistDetailDto(Artist artist){
        this.artistId = artist.getArtistId();
        this.profile = artist.getProfile();
        this.backGround = artist.getBackGround();
        this.name = artist.getName();
        this.intro = artist.getIntro();
        this.subTitle = artist.getSubTitle();
        this.detail = artist.getDetail();
        this.youtube = artist.getYoutube();
        this.content = artist.getContent();
    }

    public void add(ArtWorks work){
        this.works.add(work);
    }
}
