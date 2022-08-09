package com.example.demo.entity.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "item")
    private List<ItemOption> options = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<ItemImages> images = new ArrayList<>();

    @OneToMany(mappedBy = "itemId")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "itemReviewId")
    private List<ItemReviewBoard> itemReviewBoards = new ArrayList<>();
}
