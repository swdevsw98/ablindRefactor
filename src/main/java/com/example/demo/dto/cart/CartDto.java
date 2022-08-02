package com.example.demo.dto.cart;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDto {

    private String email;

    private Long itemId;

    private Long count;

    private Long id;

}
