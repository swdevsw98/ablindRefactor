package com.example.demo.entity.cart;

import com.example.demo.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Delivery extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    private String address;
    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus deliveryStatus; //READY, GO, CANCEL
}
