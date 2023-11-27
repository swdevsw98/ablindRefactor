package com.example.demo.dto.order;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDetailDto {

    //주문정보
    private String orderStatus;
    private Long price;
    private String createdAt;
    private Long id;

    //주문자
    private String ordererName;
    private String ordererPhoneNumber;
    private String ordererAccountName;
    private String ordererAccount;

    //수령인
    private String recipientName;
    private String recipientPhoneNumber;
    private String recipientAddress;

    //주문상품
    private List<OrderItemDto> orderItems = new ArrayList<>();

    @Builder
    public OrderDetailDto (String orderStatus, Long price, String createdAt,
                           Long id, String ordererName, String ordererAccount, String ordererAccountName,
                           String ordererPhoneNumber, String recipientAddress, String recipientPhoneNumber,
                           String recipientName){
        this.ordererAccount = ordererAccount;
        this.orderStatus = orderStatus;
        this.price = price;
        this.createdAt = createdAt;
        this.id = id;
        this.ordererName = ordererName;
        this.ordererAccountName = ordererAccountName;
        this.ordererPhoneNumber = ordererPhoneNumber;
        this.recipientAddress = recipientAddress;
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
    }
}
