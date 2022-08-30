package com.example.demo.service.cart;

import com.example.demo.dto.cart.CartDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.cart.Cart;
import com.example.demo.entity.cart.CartItem;
import com.example.demo.entity.shop.Item;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.cart.CartItemRepository;
import com.example.demo.repository.cart.CartRepository;
import com.example.demo.repository.shop.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;

    public void addCart(String email,CartDto cartDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("없는 고객입니다."));

        if(member.getCartId() == null)
        {
            Cart cart = new Cart();
            cartRepository.save(cart);
            member.setCartId(cart);
        }

        Cart cart = cartRepository.findById(member.getCartId().getId())
                .orElseThrow(()-> new IllegalStateException("그런 카트 없어요"));
        CartItem cartItem = cartItemRepository.findByCartIdAndItemIdAndOptionName(cart, cartDto.getItemId(), cartDto.getItemOption())
                .orElseGet(()->new CartItem());

        cartItem.setCartId(cart);
        cartItem.setItemId(cartDto.getItemId());
        cartItem.setOptionName(cartDto.getItemOption());
        if (cartItem.getCount() == null) {
           cartItem.setCount(cartDto.getCount());
        } else {
            cartItem.setCount(cartItem.getCount() + cartDto.getCount());
        }

        cartItemRepository.save(cartItem);
    }

    public List<CartDto> getCartItemList(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("없는 고객"));
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(member.getCartId())
                .orElseThrow(() -> new IllegalStateException("없는 장바구니"));
        List<CartDto> cartDtos = new ArrayList<>();

        for(CartItem cartItem : cartItems){
            Item item = itemRepository.findById(cartItem.getItemId())
                    .orElseThrow(() -> new IllegalStateException("그런 상품 없음"));

            CartDto cartDto = CartDto.builder()
                    .itemId(cartItem.getItemId())
                    .count(cartItem.getCount())
                    .id(cartItem.getId())
                    .itemImage(item.getImages().get(0).getUrl())
                    .itemName(item.getName())
                    .itemOption(cartItem.getOptionName())
                    .price(item.getPrice() * cartItem.getCount())
                    .build();

            cartDtos.add(cartDto);
        }

        return cartDtos;
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
