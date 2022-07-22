package com.example.demo.repository.artist;

import com.example.demo.entity.artist.ArtWorks;
import com.example.demo.entity.artist.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtistWorkRepository extends JpaRepository<ArtWorks, Long> {
    Optional<List<ArtWorks>> findAllByArtistWorkId(Artist artistId);
}
