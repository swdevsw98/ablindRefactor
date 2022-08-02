package com.example.demo.repository.shop;

import com.example.demo.entity.shop.ShopBanner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopBannerRepository extends JpaRepository<ShopBanner, Long> {
    List<ShopBanner> findAll();
}
