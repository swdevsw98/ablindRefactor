package com.example.demo.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderFinalDto {
    private OrdererDto ordererDto;
    private RecipientDto recipientDto;
    private List<OrderItemDto> orderItemDtoList;
    private Long price;
}
