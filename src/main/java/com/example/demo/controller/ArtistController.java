package com.example.demo.controller;

import com.example.demo.dto.ArtistBoardDto;
import com.example.demo.dto.ArtistInfoDto;
import com.example.demo.entity.Artist;
import com.example.demo.entity.ArtistBoard;
import com.example.demo.repository.ArtistBoardRepository;
import com.example.demo.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.PrePersist;


@RequestMapping("/artist")
@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistRepository artistRepository;
    private final ArtistBoardRepository artistBoardRepository;

    //artist 개인페이지
    @GetMapping("/{artistId}")
    public ArtistInfoDto privateArtist(@PathVariable(name = "artistId") Long artist_id) throws NullPointerException {
        Artist artist = artistRepository.findByArtistId(artist_id)
                .orElseThrow(IllegalStateException::new);
        ArtistInfoDto artistInfoDto = new ArtistInfoDto(artist);
        return artistInfoDto;
    }

    //응원글 가져오기
    @GetMapping("/{artistId}/board/{boardId}")
    public ArtistBoardDto postBoard(@PathVariable(name = "artistId") Artist artist_id, @PathVariable(name = "boardId") Long board_id){
        ArtistBoard artistBoard = artistBoardRepository.findByArtistIdAndId(artist_id, board_id)
                .orElseThrow(IllegalStateException::new);
        ArtistBoardDto artistBoardDto = new ArtistBoardDto(artistBoard);
        return artistBoardDto;
    }


}
