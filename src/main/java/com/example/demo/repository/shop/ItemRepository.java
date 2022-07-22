package com.example.demo.repository.shop;

import com.example.demo.entity.shop.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAll();

    //contain = like와 같음
    Optional<List<Item>> findByNameContaining(String Keyword);

    Optional<List<Item>> findByCategoryContaining(String Keyword);

    Optional<Item> findByName(String name);
}
