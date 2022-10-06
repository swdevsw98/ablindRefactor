package com.example.demo.repository.shop;

import com.example.demo.entity.Member;
import com.example.demo.entity.shop.Item;
import com.example.demo.entity.shop.ItemReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemReviewRepository extends JpaRepository<ItemReviewBoard, Long> {
    Optional<List<ItemReviewBoard>> findByItemReviewId(Item item_id);

    Optional<ItemReviewBoard> findByItemReviewId(Long item_id);
    Optional<ItemReviewBoard> deleteAllByMember(Member member);
}
