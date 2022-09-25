package com.example.demo.dto.admin;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MainBannerDto {

    private Long id;
    private String image;
    private String deleteImage;
    private String content;
    private String link;
}
