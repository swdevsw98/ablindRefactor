package com.example.demo.repository.shop;

import com.example.demo.entity.shop.ItemImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemImagesRepository extends JpaRepository<ItemImages, Long> {
}
