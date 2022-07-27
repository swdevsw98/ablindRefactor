package com.example.demo.dto.shop;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ItemReviewDto {

    private Long reviewBoardId;

    private String title;

    private String content;

    @Builder
    public ItemReviewDto (Long reviewBoardId, String title, String content){
        this.reviewBoardId = reviewBoardId;
        this.title = title;
        this.content = content;
    }
}
