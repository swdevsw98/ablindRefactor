package com.example.demo.dto.shop;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {

    private Long id;

    private String email;

    private String item;

    private Long count;

    @Builder
    public OrderDto(Long id, String email, String item, Long count) {
        this.id = id;
        this.email = email;
        this.item = item;
        this.count = count;
    }

}
