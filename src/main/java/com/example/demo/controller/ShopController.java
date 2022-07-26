package com.example.demo.controller;

import com.example.demo.dto.shop.ItemDto;
import com.example.demo.dto.shop.ItemReviewDto;
import com.example.demo.dto.shop.OrderDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.shop.Item;
import com.example.demo.entity.shop.ItemReviewBoard;
import com.example.demo.entity.shop.Order;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.shop.ItemRepository;
import com.example.demo.service.shop.ItemReviewService;
import com.example.demo.service.shop.OrderService;
import com.example.demo.service.shop.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final ItemReviewService itemReviewService;


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
    @ResponseBody
    public OrderDto order(@RequestBody OrderDto orderDto) {
        Member member = memberRepository.findByEmail(orderDto.getEmail())
                .orElseThrow(() -> new IllegalStateException("그런 고객 없어요"));
        Item item = itemRepository.findByName(orderDto.getItem())
                .orElseThrow(() -> new IllegalStateException("그런 상품 없어요"));
        return orderService.save(member, item, orderDto.getCount());
    }

    //order 변경
    @PutMapping("/order/update")
    public ResponseEntity updateOrder(@RequestBody OrderDto orderDto) {
        Item item = itemRepository.findByName(orderDto.getItem())
                .orElseThrow(() -> new IllegalStateException("그런 상품 없어요"));
        return orderService.updateOrder(item, orderDto.getCount(), orderDto.getId());
    }

    //order 취소
    @DeleteMapping("/order/cancel")
    public ResponseEntity cancelOrder(@RequestBody OrderDto orderDto) {
            return orderService.cancelOrder(orderDto.getId());
    }

    //review 게시판 불러오기
    @GetMapping("/{itemId}/review")
    public List<ItemReviewDto> listReviewBoard(@PathVariable(name = "itemId") Item item_id){
        List<ItemReviewDto> list = itemReviewService.getReviewBoardList(item_id);
        return list;
    }

    //review 게시판 생성
    @PostMapping("/{itemId}/review")
    public ResponseEntity writeReview(@PathVariable(name = "itemId") Long item_id, @RequestBody Map<String, String> Board){
        Item item = itemRepository.findById(item_id)
                .orElseThrow(() -> new IllegalStateException("없는 상품 리뷰 등록"));
        ItemReviewDto itemReviewDto = ItemReviewDto.builder()
                .itemId(item.getId())
                .title(Board.get("title"))
                .content(Board.get("content"))
                .build();
        itemReviewService.writeReview(item, itemReviewDto);
        return new ResponseEntity<>("게시글 등록 완료", HttpStatus.OK);
    }
}
