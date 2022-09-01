package com.example.demo.dto.order;

import lombok.Builder;
import lombok.Data;

@Data
public class OrderListDto {

    private String orderStatus;
    private String name;
    private Long price;
    private String createdAt;
    private Long id;

    @Builder
    private OrderListDto (String orderStatus, String name, String createdAt,
                          Long id, Long price){
        this.orderStatus = orderStatus;
        this.name = name;
        this.createdAt = createdAt;
        this.id = id;
        this.price = price;
    }
}
