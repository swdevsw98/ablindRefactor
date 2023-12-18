package com.example.demo.entity.shop;


import com.example.demo.dto.shop.ArtistProductRequest;
import com.example.demo.entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor
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
    private Integer salesNum;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;


    public ArtistProduct(Member member, ArtistProductRequest artistProductRequest,
                         String image){
        this.member = member;
        this.title = artistProductRequest.getTitle();
        this.description = artistProductRequest.getDescription();
        this.price = artistProductRequest.getPrice();
        this.image = image;
        this.salesNum = 0;
    }

    public void addSalesNum(){
        this.salesNum++;
    }
}
