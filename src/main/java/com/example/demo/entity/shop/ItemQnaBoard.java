package com.example.demo.entity.shop;

import com.example.demo.entity.BaseEntity;
import com.example.demo.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Table(name = "item_Qna")
@Entity
public class ItemQnaBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_qna_id")
    private Item itemQnaId;

    private String title;

    private String content;

    private boolean secretTNF;

    @OneToOne
    private Member member;

    @Builder
    public ItemQnaBoard(Item item, String title, String content,
                        Member member, boolean secretTNF){
        this.itemQnaId = item;
        this.title = title;
        this.content = content;
        this.member = member;
        this.secretTNF = secretTNF;
    }

}
