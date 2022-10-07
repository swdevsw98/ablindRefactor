package com.example.demo.dto.order;

import com.example.demo.entity.shop.OrderItem;
import lombok.Data;

@Data
public class MypageOrderItemDto {
    private Long id;
    private String itemName;
    private String itemOption;
    private Long orderPrice;
    private Long count;

    public MypageOrderItemDto(OrderItem orderItem){
        this.id = orderItem.getId();
        this.itemName = orderItem.getItemId().getName();
        this.itemOption = orderItem.getItemOption();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }
}
