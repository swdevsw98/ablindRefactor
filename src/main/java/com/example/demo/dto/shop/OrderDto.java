package com.example.demo.dto.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {

    private String email;

    private String item;

    private Long count;

}
