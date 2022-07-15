package com.example.demo.repository;

import com.example.demo.entity.Artist;
import com.example.demo.entity.Follow;
import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistFollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowArtistIdAndFollowUserId(Artist artist, Member member);
}
