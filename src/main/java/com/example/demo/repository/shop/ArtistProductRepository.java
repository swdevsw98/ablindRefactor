package com.example.demo.repository.shop;

import com.example.demo.entity.shop.ArtistProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistProductRepository extends JpaRepository<ArtistProduct, Long> {
}
