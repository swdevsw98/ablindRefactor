package com.example.demo.dto.shop;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ItemReviewDto {

    private Long reviewBoardId;

    private String title;

    private String content;

    private BigDecimal rate;

    private String image;


    @Builder
    public ItemReviewDto (Long reviewBoardId, String title, String content,
                          BigDecimal rate, String image){
        this.reviewBoardId = reviewBoardId;
        this.title = title;
        this.content = content;
        this.rate = rate;
        this.image = image;
    }
}
