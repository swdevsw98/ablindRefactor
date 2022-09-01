package com.example.demo.entity.shop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Table(name = "order_item")
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @JsonIgnore
    private Item itemId;

    private Long orderPrice;

    private Long count;

    @Builder
    public OrderItem(Order order, Item item, Long count)
    {
        this.orderId = order;
        this.itemId = item;
        this.orderPrice = item.getPrice() * count;
        this.count = count;
    }
}
