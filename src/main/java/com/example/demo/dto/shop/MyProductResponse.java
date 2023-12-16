package com.example.demo.dto.shop;

import lombok.Data;

@Data
public class MyProductResponse {
    private String title;
    private String description;
    private Integer price;
    private String image;
    private Integer salesNum;

    public MyProductResponse(String image, String title, String description, Integer price,
                             Integer salesNum){
        this.image = image;
        this.title = title;
        this.description = description;
        this.price = price;
        this.salesNum = salesNum;
    }
}
