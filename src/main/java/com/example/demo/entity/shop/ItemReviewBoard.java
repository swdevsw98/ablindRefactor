package com.example.demo.entity.shop;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Table(name = "item_review")
@Entity
public class ItemReviewBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_review_id")
    private Item itemReviewId;

    private BigDecimal rate;

    private String image;

    private String deleteImage;

    private String title;

    private String content;

    @Builder
    public ItemReviewBoard(Item item, String title, String content,
                           BigDecimal rate, String image, String deleteImage){
        this.itemReviewId = item;
        this.title = title;
        this.content = content;
        this.rate = rate;
        this.image = image;
        this.deleteImage = deleteImage;
    }
}
