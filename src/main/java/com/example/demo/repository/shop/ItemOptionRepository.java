package com.example.demo.repository.shop;

import com.example.demo.entity.shop.Item;
import com.example.demo.entity.shop.ItemOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemOptionRepository extends JpaRepository<ItemOption, Long> {
    Optional<ItemOption> deleteAllByItem(Item item);
}
