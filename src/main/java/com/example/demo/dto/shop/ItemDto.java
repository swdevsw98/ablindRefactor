package com.example.demo.dto.shop;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ItemDto {

    private Long itemId;

    private String name;

    private Long price;

    private String author;

    private String url;

    @Builder
    public ItemDto(Long itemId, String name, Long price, String author, String url){
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.author = author;
        this.url = url;
    }
}
