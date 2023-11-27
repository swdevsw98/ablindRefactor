package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.MemberDataDto;
import com.example.demo.dto.MemberFormDto;
import com.example.demo.dto.artist.ArtistDetailDto;
import com.example.demo.dto.cart.CartDto;
import com.example.demo.dto.order.OrderDetailDto;
import com.example.demo.dto.order.OrderListDto;
import com.example.demo.service.cart.CartService;
import com.example.demo.service.mypage.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RequestMapping("/mypage")
@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final CartService cartService;
    private final MypageService mypageService;
    private final JwtTokenProvider jwtTokenProvider;

    //유저 정보 가져오기
    @GetMapping("")
    public MemberDataDto getMemberData(ServletRequest request){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        return mypageService.getMemberData(jwtTokenProvider.getUserPk(token));
    }

    //구독 아티스트 정보 가져오기
    @GetMapping("/follow/artist")
    public List<ArtistDetailDto> getFollowArtist(ServletRequest request) {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        return mypageService.getFollowArtist(jwtTokenProvider.getUserPk(token));
    }

    //장바구니 가져오기
    @GetMapping("/cart")
    public List<CartDto> getCartItemList(ServletRequest request){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        return cartService.getCartItemList(jwtTokenProvider.getUserPk(token));
    }

    //장바구니 담기
    @PostMapping("/cart/add")
    public Long addCart(ServletRequest request,
                                  @RequestBody CartDto cartDto){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        return cartService.addCart(jwtTokenProvider.getUserPk(token),cartDto);
    }

    //장바구니 수정
    @PutMapping("/cart/update")
    public ResponseEntity updateCart(@RequestBody CartDto cartDto){

        cartService.updateCart(cartDto);

        return new ResponseEntity("장바구니 갯수 수정~~", HttpStatus.OK);
    }

    //장바구니 삭제
    @DeleteMapping("/cart/delete")
    public ResponseEntity deleteCartItem(@RequestBody CartDto cartDto){
        cartService.deleteCart(cartDto);
        return new ResponseEntity("아이템 삭제~", HttpStatus.OK);
    }

    //주문내역 조회
    @GetMapping("/order")
    public List<OrderListDto> getOrderList(ServletRequest request){
        return mypageService.getOrderList(getEmail(request));
    }

    //주문상세 조회
    @GetMapping("order/detail")
    public OrderDetailDto getOrderDetail(@RequestParam(value="id") Long id) {
        return mypageService.getOrderDetail(id);
    }

    //유저 프로필 업데이트
    @PutMapping("profile/update")
    public ResponseEntity updateProfile(ServletRequest request,
                                        @RequestPart(value = "file", required = false)MultipartFile multipartFile) throws IOException{
        return mypageService.updateProfile(getEmail(request), multipartFile);
    }

    //유저 정보 수정
    @PutMapping("info/update")
    public ResponseEntity updateMemberInfo(ServletRequest request,
                                           @RequestBody MemberFormDto memberFormDto){
        return mypageService.updateMemberInfo(getEmail(request), memberFormDto);
    }

    private String getEmail(ServletRequest request){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        return jwtTokenProvider.getUserPk(token);
    }
}
