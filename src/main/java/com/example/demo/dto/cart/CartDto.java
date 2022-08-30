package com.example.demo.dto.cart;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDto {

    private Long itemId;

    private Long count;

    private Long id;

    private String itemImage;

    private String itemName;

    private String itemOption;

    private Long price;

    @Builder
    public CartDto (Long itemId, Long count, Long id, String itemImage,
                    String itemName, String itemOption, Long price) {
        this.itemId = itemId;
        this.count = count;
        this.id = id;
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemOption = itemOption;
        this.price = price;
    }
}
