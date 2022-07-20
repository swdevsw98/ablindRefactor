package com.example.demo.repository;

import com.example.demo.entity.ArtWorks;
import com.example.demo.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtistWorkRepository extends JpaRepository<ArtWorks, Long> {
    Optional<List<ArtWorks>> findAllByArtistWorkId(Artist artistId);
}
