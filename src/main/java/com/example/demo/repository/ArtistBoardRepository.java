package com.example.demo.repository;

import com.example.demo.entity.Artist;
import com.example.demo.entity.ArtistBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtistBoardRepository extends JpaRepository<ArtistBoard, Long> {
    List<ArtistBoard> findByArtistId(Artist artist_id);
}
