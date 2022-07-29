package com.example.demo.controller;

import com.example.demo.dto.cart.CartDto;
import com.example.demo.entity.cart.CartItem;
import com.example.demo.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/mypage")
@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final CartService cartService;

    //장바구니 가져오기
    @GetMapping("/cart")
    public List<CartItem> getCartItemList(@RequestBody CartDto cartDto){
        return cartService.getCartItemList(cartDto);
    }

    //장바구니 담기
    @PostMapping("/cart/add")
    public ResponseEntity addCart(@RequestBody CartDto cartDto){
        cartService.addCart(cartDto);

        return new ResponseEntity("장바구니에 추가 됐습니다~", HttpStatus.OK);
    }
}
