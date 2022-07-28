package com.example.demo.dto.shop;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ItemQnaDto {


    private Long qnaBoardId;

    private String title;

    private String content;

    @Builder
    public ItemQnaDto (Long qnaBoardId, String title, String content){
        this.qnaBoardId = qnaBoardId;
        this.title = title;
        this.content = content;
    }

}
