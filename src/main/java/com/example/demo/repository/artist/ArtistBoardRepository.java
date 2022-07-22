package com.example.demo.repository.artist;

import com.example.demo.entity.artist.Artist;
import com.example.demo.entity.artist.ArtistBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistBoardRepository extends JpaRepository<ArtistBoard, Long> {
    List<ArtistBoard> findByArtistId(Artist artist_id);
}
