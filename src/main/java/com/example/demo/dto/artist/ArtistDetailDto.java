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

    private String email;

    private List<ArtWorks> works = new ArrayList<>();


    public ArtistDetailDto(Artist artist){
        this.artistId = artist.getId();
        this.profile = artist.getProfile();
        this.backGround = artist.getBackGround();
        this.name = artist.getName();
        this.intro = artist.getIntro();
        this.subTitle = artist.getSubTitle();
        this.detail = artist.getDetail();
        this.youtube = artist.getYoutube();
        this.content = artist.getContent();
    }

    @Builder
    public ArtistDetailDto(Long artistId, String profile, String name,
                            String intro, String subTitle, String content, String email){
        this.artistId = artistId;
        this.profile = profile;
        this.name = name;
        this.intro = intro;
        this.subTitle = subTitle;
        this.content = content;
        this.email = email;
    }

    public void add(ArtWorks work){
        this.works.add(work);
    }
}
