package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "main_banner")
@NoArgsConstructor
public class MainBanner {

    @Id @GeneratedValue
    @Column(name = "main_banner_id")
    private Long id;

    private String image;

    private String deleteImage;

    private String content;

    private String link;

    public MainBanner(String image, String deleteImage, String content, String link){
        this.image = image;
        this.deleteImage = deleteImage;
        this.content = content;
        this.link = link;
    }
}
