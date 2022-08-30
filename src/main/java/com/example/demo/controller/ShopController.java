package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.MemberDataDto;
import com.example.demo.dto.order.OrderFinalDto;
import com.example.demo.dto.order.OrderItemDto;
import com.example.demo.dto.order.OrdererDto;
import com.example.demo.dto.order.RecipientDto;
import com.example.demo.dto.shop.ItemDto;
import com.example.demo.dto.shop.ItemQnaDto;
import com.example.demo.dto.shop.ItemReviewDto;
import com.example.demo.dto.shop.OrderDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.shop.*;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.shop.ItemRepository;
import com.example.demo.repository.shop.ShopBannerRepository;
import com.example.demo.service.shop.ItemQnaService;
import com.example.demo.service.shop.ItemReviewService;
import com.example.demo.service.shop.OrderService;
import com.example.demo.service.shop.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequestMapping("/shop")
@RestController
@RequiredArgsConstructor
public class ShopController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ShopService shopService;
    private final MemberRepository memberRepository;
    private final OrderService orderService;
    private final ItemRepository itemRepository;
    private final ItemReviewService itemReviewService;
    private final ItemQnaService itemQnaService;
    private final ShopBannerRepository shopBannerRepository;

    //shop main
    @GetMapping("")
    public List<ItemDto> listItem() {
        List<ItemDto> list = shopService.list();

        return list;
    }

    @GetMapping("/detail/{itemId}")
    public ItemDto listItemDetail(@PathVariable(name = "itemId")Long itemId) {
        return shopService.detailList(itemId);
    }

    //shop banner
    @GetMapping("/banner")
    public List<ShopBanner> listBanner() {
        return shopBannerRepository.findAll();
    }

    //shop 검색 필터
    @GetMapping("/search/filter")
    public List<ItemDto> filterItem(@RequestBody Map<String, String> keyword) {
        List<ItemDto> list = shopService.titleFilter(keyword.get("keyword"));

        return list;
    }

    //shop 카테고리 필터
    @GetMapping("/category/filter")
    public List<ItemDto> filterCategory(@RequestParam(value = "keyword") String keyword) {
        List<ItemDto> list = shopService.categoryFilter(keyword);

        return list;
    }

    //정보가져오기
    @GetMapping("/order/member")
    public MemberDataDto getMember(ServletRequest request){
        return orderService.getMemberInfo(getEmail(request));
    }

    //order 주문(장바구니)
    @PostMapping("/order")
    @ResponseBody
    public ResponseEntity order(ServletRequest request,
                                @RequestBody OrderFinalDto orderFinalDto) {
        return orderService.save(getEmail(request), orderFinalDto);
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
    public List<ItemReviewDto> listReviewBoard(@PathVariable(name = "itemId") Item item_id,
                                               ServletRequest request) {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        List<ItemReviewDto> list = itemReviewService.getReviewBoardList(item_id, jwtTokenProvider.getUserPk(token));
        return list;
    }
    //review 게시판 생성

    @PostMapping("/{itemId}/review")
    public ResponseEntity writeReview(@PathVariable(name = "itemId") Long item_id,
                                      @RequestPart(value = "file", required = false)MultipartFile multipartFile,
                                      @RequestPart(value = "ItemReviewDto") ItemReviewDto itemReviewDto,
                                      ServletRequest request) throws IOException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        Item item = itemRepository.findById(item_id)
                .orElseThrow(() -> new IllegalStateException("없는 상품 리뷰 등록"));
        itemReviewService.writeReview(item, itemReviewDto, multipartFile, jwtTokenProvider.getUserPk(token));
        return new ResponseEntity<>("게시글 등록 완료", HttpStatus.OK);
    }
    //review 게시판 수정

    @PutMapping("/{itemId}/review/update")
    public ResponseEntity updateReview(@RequestPart(value = "file", required = false) MultipartFile multipartFile,
                                       @RequestPart(value = "ItemReviewDto") ItemReviewDto itemReviewDto) throws IOException{
        itemReviewService.updateReview(itemReviewDto, multipartFile);
        return new ResponseEntity("게시글 수정 완료", HttpStatus.OK);
    }
    //review게시판 삭제

    @DeleteMapping("/{itemId}/review/delete")
    public ResponseEntity deleteReview(@RequestBody ItemReviewDto itemReviewDto){
        itemReviewService.deleteReview(itemReviewDto);
        return new ResponseEntity("게시글 삭제 완료", HttpStatus.OK);
    }
    //qna 게시판 불러오기

    @GetMapping("/{itemId}/qna")
    public List<ItemQnaDto> listQnaBoard(@PathVariable(name = "itemId") Item item_id,
                                         ServletRequest request) {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        List<ItemQnaDto> list = itemQnaService.getQnaBoardList(item_id, jwtTokenProvider.getUserPk(token));
        return list;
    }
    //qna 게시판 생성

    @PostMapping("/{itemId}/qna")
    public ResponseEntity writeQna(@PathVariable(name = "itemId") Long item_id,
                                   @RequestBody ItemQnaDto itemQnaDto,
                                   ServletRequest request) {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        Item item = itemRepository.findById(item_id)
                .orElseThrow(() -> new IllegalStateException("없는 상품 리뷰 등록"));
        itemQnaService.writeQna(item, itemQnaDto, jwtTokenProvider.getUserPk(token));
        return new ResponseEntity<>("게시글 등록 완료", HttpStatus.OK);
    }
    //qna 게시판 수정

    @PutMapping("/{itemId}/qna/update")
    public ResponseEntity updateQna(@RequestBody ItemQnaDto itemQnaDto){
        itemQnaService.updateQna(itemQnaDto);
        return new ResponseEntity("게시글 수정 완료", HttpStatus.OK);
    }
    //qna게시판 삭제

    @DeleteMapping("/{itemId}/qna/delete")
    public ResponseEntity deleteQna(@RequestBody ItemQnaDto itemQnaDto){
        itemQnaService.deleteQna(itemQnaDto);
        return new ResponseEntity("게시글 삭제 완료", HttpStatus.OK);
    }
    private String getEmail(ServletRequest request){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        return jwtTokenProvider.getUserPk(token);
    }
}
