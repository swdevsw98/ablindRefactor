package com.example.demo.entity.cart;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Table(name = "cart")
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cartId", cascade = CascadeType.ALL)
    @Column(name = "cart_item_id")
    private List<CartItem> cartItem = new ArrayList<>();
}
