package com.example.demo.entity.shop;

import com.example.demo.entity.BaseEntity;
import com.example.demo.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Table(name = "item_review")
@Entity
public class ItemReviewBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_review_id")
    private Item itemReviewId;

    @Column(precision = 2, scale = 1)
    private BigDecimal rate;

    private String image;

    private String deleteImage;

    private String title;

    private String content;

    @OneToOne
    private Member member;

    @Builder
    public ItemReviewBoard(Item item, String title, String content,
                           BigDecimal rate, String image, String deleteImage,
                           Member member){
        this.itemReviewId = item;
        this.title = title;
        this.content = content;
        this.rate = rate;
        this.image = image;
        this.deleteImage = deleteImage;
        this.member = member;
    }
}
