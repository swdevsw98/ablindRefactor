package com.example.demo.repository.shop;

import com.example.demo.entity.shop.Item;
import com.example.demo.entity.shop.ItemImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemImagesRepository extends JpaRepository<ItemImages, Long> {
    Optional<List<ItemImages>> findByItem(Item item);
    Optional<ItemImages> deleteAllByItem(Item item);
}
