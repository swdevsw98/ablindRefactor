package com.example.demo.entity.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Table(name = "item")
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String author;

    private Long price;

    private String category;

    private String detailImg;

    @Column(precision = 2, scale = 1)
    private BigDecimal rate;

    @OneToMany(mappedBy = "item")
    private List<ItemOption> options = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<ItemImages> images = new ArrayList<>();

    @OneToMany(mappedBy = "itemId")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "itemReviewId")
    private List<ItemReviewBoard> itemReviewBoards = new ArrayList<>();


    //==계산 메소드==//
    public void avgAddRate(BigDecimal rate){
        if(itemReviewBoards.size() == 1) {
            this.rate = this.rate.add(rate);
        } else {
            BigDecimal div = new BigDecimal("2");
            this.rate = this.rate.add(rate).divide(div ,1, RoundingMode.HALF_EVEN);
        }
    }

    public void avgUpdateRate(BigDecimal preRate, BigDecimal updateRate) {
        BigDecimal mul = new BigDecimal("2");
        this.rate = this.rate.multiply(mul).subtract(preRate).add(updateRate).divide(mul, 1, RoundingMode.HALF_EVEN);
    }

    public void avgDeleteRate(BigDecimal rate) {
        BigDecimal mul = new BigDecimal("2");
        this.rate = this.rate.multiply(mul).subtract(rate);
    }
}
