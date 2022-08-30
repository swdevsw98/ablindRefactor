package com.example.demo.entity.shop;

import com.example.demo.dto.order.OrdererDto;
import com.example.demo.dto.order.RecipientDto;
import com.example.demo.entity.BaseEntity;
import com.example.demo.entity.Member;
import com.example.demo.entity.cart.Delivery;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Table(name = "orders")
@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    private String orderStatus;
    //총 금액
    private Long price;

    //주문자 정보
    private String ordererName;
    private String ordererPhoneNumber;
    private String ordererAccountName;
    private String ordererAccount;

    //수령인 정보
    private String recipientName;
    private String recipientPhoneNumber;
    private String recipientAddress;


    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;


    public void orderAdd(Member member, String orderStatus, Long price) {
        this.memberId = member;
        this.orderStatus = orderStatus;
        this.price = price;
    }

    public Order(OrdererDto ordererDto, RecipientDto recipientDto){
        this.ordererName = ordererDto.getName();
        this.ordererPhoneNumber = ordererDto.getPhoneNumber();
        this.ordererAccount = ordererDto.getAccount();
        this.ordererAccountName = ordererDto.getAccount_name();
        this.recipientName = recipientDto.getName();
        this.recipientAddress = recipientDto.getAddress();
        this.recipientPhoneNumber = recipientDto.getPhoneNumber();
    }


}
