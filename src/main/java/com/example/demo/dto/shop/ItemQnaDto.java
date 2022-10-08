package com.example.demo.dto.shop;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ItemQnaDto {

    private Long itemId;
    private Long qnaBoardId;
    private String title;
    private String content;
    private boolean secretTNF;
    private boolean myReview;
    private String username;
    private String createdAt;
    private String updatedAt;
    private String answer;

    @Builder
    public ItemQnaDto (Long qnaBoardId, String title, String content,
                       boolean secretTNF, boolean myReview, String username,
                       String createdAt, String updatedAt, String answer,
                       Long itemId){
        this.itemId = itemId;
        this.qnaBoardId = qnaBoardId;
        this.title = title;
        this.content = content;
        this.secretTNF = secretTNF;
        this.myReview = myReview;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.answer = answer;
    }

}
