package com.example.demo.repository.cart;

import com.example.demo.entity.cart.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
