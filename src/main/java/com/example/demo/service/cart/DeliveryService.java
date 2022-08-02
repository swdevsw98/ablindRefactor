package com.example.demo.service.cart;

import com.example.demo.dto.cart.DeliveryDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.cart.Delivery;
import com.example.demo.entity.cart.DeliveryStatus;
import com.example.demo.entity.shop.Order;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.cart.DeliveryRepository;
import com.example.demo.repository.shop.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    //관리자가 배송하기 전
    public void startDelivery(DeliveryDto deliveryDto){
        Member member = memberRepository.findByEmail(deliveryDto.getEmail())
                .orElseThrow(() -> new IllegalStateException("없는 고객"));
        Order order = orderRepository.findById(deliveryDto.getOrderId())
                .orElseThrow(() -> new IllegalStateException("없는 주문"));

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setDeliveryStatus(DeliveryStatus.READY);

        order.setDelivery(delivery);

        orderRepository.save(order);
        deliveryRepository.save(delivery);
    }

    //관리자가 배송 후 배송버튼 누름
}
