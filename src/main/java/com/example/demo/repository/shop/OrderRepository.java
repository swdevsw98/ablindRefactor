package com.example.demo.repository.shop;

import com.example.demo.entity.shop.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
