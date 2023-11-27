package com.example.demo.dto.order;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderListDto {

    private String orderStatus;
    private Long price;
    private String createdAt;
    private Long id;

    private List<MypageOrderItemDto> orderItems = new ArrayList<>();

    @Builder
    private OrderListDto (String orderStatus, String createdAt,
                          Long id, Long price){
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.id = id;
        this.price = price;
    }
}
