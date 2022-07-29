package com.example.demo.repository.cart;

import com.example.demo.entity.cart.Cart;
import com.example.demo.entity.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndItemId(Cart cart, Long item);

    Optional<List<CartItem>> findAllByCartId(Cart cart);
}
