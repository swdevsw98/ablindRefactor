package com.example.demo.repository.artist;

import com.example.demo.entity.artist.Artist;
import com.example.demo.entity.artist.Follow;
import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtistFollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowArtistIdAndFollowUserId(Artist artist, Member member);
    Optional<List<Follow>> findByFollowUserId(Member member);
}
