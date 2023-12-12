package com.example.demo.entity.shop;


import com.example.demo.dto.shop.ArtistProductRequest;
import com.example.demo.entity.Member;
import lombok.Data;
import org.h2.engine.User;

import javax.persistence.*;

@Data
@Entity
public class ArtistProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;
    private String title;
    private String description;
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public ArtistProduct(Member member, ArtistProductRequest artistProductRequest,
                         String image){
        this.member = member;
        this.title = artistProductRequest.getTitle();
        this.description = artistProductRequest.getDescription();
        this.price = artistProductRequest.getPrice();
        this.image = image;
    }
}
