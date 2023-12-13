package com.example.demo.dto.shop;

import lombok.Data;

@Data
public class ProductsResponse {
    private Long productId;
    private String title;
    private String description;
    private Integer price;
    private String image;

    public ProductsResponse(String image, String title, String description, Integer price,
                             Long productId){
        this.image = image;
        this.title = title;
        this.description = description;
        this.price = price;
        this.productId = productId;
    }
}
