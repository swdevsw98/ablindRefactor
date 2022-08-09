package com.example.demo.dto.shop;

import com.example.demo.entity.shop.ItemImages;
import com.example.demo.entity.shop.ItemOption;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ItemDto {

    private Long itemId;

    private String name;

    private Long price;

    private String author;

    private String detailImg;

    private List<ItemOption> options;

    private List<ItemImages> images;

    @Builder
    public ItemDto(Long itemId, String name, Long price, String author, List<ItemImages> images,
                   String detailImg, List<ItemOption> options){
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.author = author;
        this.images = images;
        this.detailImg = detailImg;
        this.options = options;
    }
}
