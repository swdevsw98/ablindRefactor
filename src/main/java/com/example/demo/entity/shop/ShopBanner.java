package com.example.demo.entity.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "shop_banner")
@NoArgsConstructor
public class ShopBanner {

    @Id @GeneratedValue
    @Column(name = "shop_banner_id")
    private Long id;

    private String image;

    private String deleteImage;

    private String content;

    private String link;

    public ShopBanner(String image, String deleteImage, String content, String link){
        this.image = image;
        this.deleteImage = deleteImage;
        this.content = content;
        this.link = link;
    }
}
