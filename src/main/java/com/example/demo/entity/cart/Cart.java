package com.example.demo.entity.cart;

import com.example.demo.entity.Member;
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

    @OneToOne
    private Member member;

    @OneToMany(mappedBy = "cartId", cascade = CascadeType.ALL)
    private List<CartItem> cartItem = new ArrayList<>();
}
