package com.example.demo.service.shop;

import com.example.demo.dto.shop.OrderDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.shop.Item;
import com.example.demo.entity.shop.Order;
import com.example.demo.entity.shop.OrderItem;
import com.example.demo.repository.shop.OrderItemRepository;
import com.example.demo.repository.shop.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
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
    public OrderDto save(Member member, Item item, Long count){
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

        OrderDto orderDto = OrderDto.builder()
                .id(order.getId())
                .item(item.getName())
                .count(count)
                .build();

        return orderDto;
    }

    public ResponseEntity updateOrder(Item item, Long count, Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("그런 주문 없어요"));
        if(count == 0) {
            orderRepository.deleteById(order.getId());
            return new ResponseEntity("주문이 취소 되었습니다." , HttpStatus.OK);

        } else{
            OrderItem orderItem = orderItemRepository.findByOrderIdAndItemId(order, item)
                    .orElseThrow(()-> new IllegalStateException("상품 주문 기록이 없어요"));

           orderItem.setCount(count);
           orderItem.setOrderPrice(item.getPrice() * count);

            orderItemRepository.save(orderItem);

            return new ResponseEntity("주문변경완료", HttpStatus.OK);
        }
    }

    public ResponseEntity cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("그런 주목 없어요"));
        orderRepository.deleteById(order.getId());
        return new ResponseEntity("주문이 취소 되었습니다.", HttpStatus.OK);
    }
}
