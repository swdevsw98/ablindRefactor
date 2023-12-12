package com.example.demo.dto.shop;


import lombok.Data;

@Data
public class ArtistProductRequest {
    private String title;
    private String description;
    private Integer price;
}
