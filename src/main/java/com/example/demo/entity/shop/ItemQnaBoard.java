package com.example.demo.entity.shop;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Table(name = "item_Qna")
@Entity
public class ItemQnaBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_qna_id")
    private Item itemQnaId;

    private String title;

    private String content;

    @Builder
    public ItemQnaBoard(Item item, String title, String content){
        this.itemQnaId = item;
        this.title = title;
        this.content = content;
    }

}
