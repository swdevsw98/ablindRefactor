package com.example.demo.repository.shop;

import com.example.demo.entity.Member;
import com.example.demo.entity.shop.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long orderId);
    Optional<List<Order>> findByMemberId(Member member);
}
