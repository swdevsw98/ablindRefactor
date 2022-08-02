package com.example.demo.service.cart;

import com.example.demo.dto.cart.CartDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.cart.Cart;
import com.example.demo.entity.cart.CartItem;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.cart.CartItemRepository;
import com.example.demo.repository.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public void addCart(CartDto cartDto) {
        Member member = memberRepository.findByEmail(cartDto.getEmail())
                .orElseThrow(() -> new IllegalStateException("없는 고객입니다."));

        if(member.getCartId() == null)
        {
            Cart cart = new Cart();
            cartRepository.save(cart);
            member.setCartId(cart.getId());
        }

        Cart cart = cartRepository.findById(member.getCartId())
                .orElseThrow(()-> new IllegalStateException("그런 카트 없어요"));
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart, cartDto.getItemId())
                .orElseGet(()->new CartItem());

        cartItem.setCartId(cart);
        cartItem.setItemId(cartDto.getItemId());
        if (cartItem.getCount() == null) {
           cartItem.setCount(cartDto.getCount());
        } else {
            cartItem.setCount(cartItem.getCount() + cartDto.getCount());
        }

        cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItemList(CartDto cartDto) {
        Member member = memberRepository.findByEmail(cartDto.getEmail())
                .orElseThrow(() -> new IllegalStateException("없는 고객"));
        Cart cart = cartRepository.findById(member.getCartId())
                .orElseThrow(() -> new IllegalStateException("없는 카트"));
        return  cartItemRepository.findAllByCartId(cart)
                .orElseThrow(() -> new IllegalStateException("없는 아이템"));
    }

    public void updateCart(CartDto cartDto){
        CartItem cartItem = cartItemRepository.findById(cartDto.getId())
                .orElseThrow(() -> new IllegalStateException("그런 아이템 없음"));
        if(cartDto.getCount() == 0){
            cartItemRepository.deleteById(cartItem.getId());
        } else{
            cartItem.setCount(cartDto.getCount());
        }
    }

    public void deleteCart(CartDto cartDto) {
        cartItemRepository.deleteById(cartDto.getId());
    }
}
