package com.example.demo.dto.order;

import com.example.demo.entity.shop.Item;
import com.example.demo.entity.shop.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderItemDto {
    private Long id;
    private Long itemId;
    private String name;
    private String img;
    private Long orderPrice;
    private Long count;
    private String itemOption;

    public void setOrderItem(OrderItem orderItem){
        this.id = orderItem.getId();
        this.itemId = orderItem.getItemId().getId();
        this.name = orderItem.getItemId().getName();
        this.img = orderItem.getItemId().getImages().get(0).getUrl();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
        this.itemOption = orderItem.getItemOption();
    }
}
