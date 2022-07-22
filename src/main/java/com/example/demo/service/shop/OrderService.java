package com.example.demo.service.shop;

import com.example.demo.entity.Member;
import com.example.demo.entity.shop.Item;
import com.example.demo.entity.shop.Order;
import com.example.demo.entity.shop.OrderItem;
import com.example.demo.repository.shop.OrderItemRepository;
import com.example.demo.repository.shop.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    //save
    public ResponseEntity save(Member member, Item item, Long count){
        Order order = Order.builder()
                .member(member)
                .orderStatus("주문완료")
                .build();

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .item(item)
                .count(count)
                .build();

        orderRepository.save(order);
        orderItemRepository.save(orderItem);

        return new ResponseEntity("주문성공", HttpStatus.OK);
    }
}
