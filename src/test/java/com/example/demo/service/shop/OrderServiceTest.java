package com.example.demo.service.shop;

import com.example.demo.dto.shop.OrderDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.shop.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired OrderService orderService;

    @Test
    public void 오더_테스트() throws Exception{
        //given
        Member member = new Member();
        member.setEmail("abc@gmail.com");

        Item item = new Item();
        item.setName("hsw");

        //when
        OrderDto orderDto = orderService.save(member, item, 3l);
        //then

        Assertions.assertEquals(item.getName(), orderDto.getItem());
     }
}