package com.example.demo.repository.shop;

import com.example.demo.entity.shop.Item;
import com.example.demo.entity.shop.ItemQnaBoard;
import com.example.demo.entity.shop.ItemReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemQnaRepository extends JpaRepository<ItemQnaBoard, Long> {

    Optional<List<ItemQnaBoard>> findByItemQnaId(Item item_id);

    Optional<ItemQnaBoard> findByItemQnaId(Long item_id);

    Optional<List<ItemQnaBoard>> findByAnswerIsNull();
}
