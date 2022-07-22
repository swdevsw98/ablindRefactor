package com.example.demo.controller;

import com.example.demo.dto.shop.ItemDto;
import com.example.demo.dto.shop.OrderDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.shop.Item;
import com.example.demo.entity.shop.Order;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.shop.ItemRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/shop")
@RestController
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final MemberRepository memberRepository;
    private final OrderService orderService;
    private final ItemRepository itemRepository;


    //shop main
    @GetMapping("")
    public List<ItemDto> listItem(){
        List<ItemDto> list = shopService.list();

        return list;
    }

    //shop 검색 필터
    @GetMapping("/search/filter")
    public List<ItemDto> filterItem(@RequestBody Map<String, String> keyword) {
        List<ItemDto> list = shopService.titleFilter(keyword.get("keyword"));

        return list;
    }

    //shop 카테고리 필터
    @GetMapping("/category/filter")
    public List<ItemDto> filterCategory(@RequestParam(value = "keyword") String keyword){
        List<ItemDto> list = shopService.categoryFilter(keyword);

        return list;
    }

    //order 주문
    @PostMapping("/order")
    public ResponseEntity order(@RequestBody OrderDto orderDto) {
        Member member = memberRepository.findByEmail(orderDto.getEmail())
                .orElseThrow(() -> new IllegalStateException("그런 고객 없어요"));
        Item item = itemRepository.findByName(orderDto.getItem())
                .orElseThrow(() -> new IllegalStateException("그런 상품 없어요"));
        return orderService.save(member, item, orderDto.getCount());
    }

    //order 변경

    //order 취소
}
