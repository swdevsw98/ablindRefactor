package com.example.demo.repository;

import com.example.demo.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByArtistId(Long artistId);

    List<Artist> findAll();
}
