package com.example.demo.repository.shop;

import com.example.demo.entity.shop.Item;
import com.example.demo.entity.shop.Order;
import com.example.demo.entity.shop.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByOrderIdAndItemId(Order orderId, Item itemId);

}
