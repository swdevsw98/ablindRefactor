package com.example.demo.controller;

import com.example.demo.dto.ArtistInfoDto;
import com.example.demo.entity.Artist;
import com.example.demo.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.PrePersist;


@RequestMapping("/artist")
@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistRepository artistRepository;


    //artist 개인페이지
    @GetMapping("/{artistId}")
    public ArtistInfoDto privateArtist(@PathVariable(name = "artistId") Long artist_id) throws NullPointerException {
        Artist artist = artistRepository.findByArtistId(artist_id)
                .orElseThrow(IllegalStateException::new);
        ArtistInfoDto artistInfoDto = new ArtistInfoDto(artist);
        return artistInfoDto;
    }


}
