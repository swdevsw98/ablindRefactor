package com.example.demo.entity.shop;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "shop_banner")
public class ShopBanner {

    @Id @GeneratedValue
    @Column(name = "shop_banner_id")
    private Long id;

    private String img;

    private String content;

    private String link;
}
