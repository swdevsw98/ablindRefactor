package com.example.demo.repository.artist;

import com.example.demo.entity.artist.Artist;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findById(Long artistId);

    List<Artist> findAll();

}
