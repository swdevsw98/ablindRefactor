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

    private boolean myReview;
    private String username;
    private String createdAt;
    private String updatedAt;


    @Builder
    public ItemReviewDto (Long reviewBoardId, String title, String content,
                          BigDecimal rate, String image, boolean myReview,
                          String username, String createdAt, String updatedAt){
        this.reviewBoardId = reviewBoardId;
        this.title = title;
        this.content = content;
        this.rate = rate;
        this.image = image;
        this.myReview = myReview;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
