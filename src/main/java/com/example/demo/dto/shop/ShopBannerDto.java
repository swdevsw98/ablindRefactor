package com.example.demo.dto.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShopBannerDto {
    private Long id;
    private String image;
    private String deleteImage;
    private String content;
    private String link;
}
