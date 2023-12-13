package com.example.demo.repository.shop;

import com.example.demo.entity.Member;
import com.example.demo.entity.shop.ArtistProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtistProductRepository extends JpaRepository<ArtistProduct, Long> {
    List<ArtistProduct> findByMember(Member member);
}
