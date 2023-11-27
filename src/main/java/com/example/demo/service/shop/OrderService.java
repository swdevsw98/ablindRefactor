package com.example.demo.service.shop;

import com.example.demo.dto.MemberDataDto;
import com.example.demo.dto.order.OrderFinalDto;
import com.example.demo.dto.order.OrderItemDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.cart.CartItem;
import com.example.demo.entity.shop.Item;
import com.example.demo.entity.shop.Order;
import com.example.demo.entity.shop.OrderItem;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.cart.CartItemRepository;
import com.example.demo.repository.cart.DeliveryRepository;
import com.example.demo.repository.shop.ItemOptionRepository;
import com.example.demo.repository.shop.ItemRepository;
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
    private final DeliveryRepository deliveryRepository;
    private final MemberRepository memberRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final ItemOptionRepository itemOptionRepository;

    //주문자 정보 받아오기
    public MemberDataDto getMemberInfo(String email){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));
        MemberDataDto memberDataDto = MemberDataDto.builder()
                .name(member.getName())
                .address(member.getAddress())
                .phoneNumber(member.getPhoneNumber())
                .account(member.getAccount())
                .account_name(member.getAccount_name())
                .build();

        return memberDataDto;
    }


    //save
    public ResponseEntity save(String email, OrderFinalDto orderFinalDto){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));
        Order order = new Order(orderFinalDto.getOrdererDto(), orderFinalDto.getRecipientDto());
        order.orderAdd(member, "주문완료", orderFinalDto.getPrice());


        for(OrderItemDto orderItemDto : orderFinalDto.getOrderItemDtoList()){
            CartItem cartItem = cartItemRepository.findById(orderItemDto.getId())
                    .orElseThrow(() -> new IllegalStateException("없는 장바구니"));
            Item item = itemRepository.findById(cartItem.getItemId())
                    .orElseThrow(() -> new IllegalStateException("없는 상품"));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .item(item)
                    .count(cartItem.getCount())
                    .option(cartItem.getOptionName())
                    .build();

            order.getOrderItems().add(orderItem);
            orderItemRepository.save(orderItem);
        }

        orderRepository.save(order);

        return new ResponseEntity(HttpStatus.OK);
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
