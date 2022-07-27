package com.example.demo.entity.shop;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private String title;

    private String content;

    @Builder
    public ItemReviewBoard(Item item, String title, String content){
        this.itemReviewId = item;
        this.title = title;
        this.content = content;
    }
}
