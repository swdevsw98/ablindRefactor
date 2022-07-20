package com.example.demo.service;

import com.example.demo.dto.ArtistBoardDto;
import com.example.demo.entity.Artist;
import com.example.demo.entity.ArtistBoard;
import com.example.demo.repository.ArtistBoardRepository;
import com.example.demo.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ArtistBoardService {
    private final ArtistBoardRepository artistBoardRepository;
    private final ArtistRepository artistRepository;

    public ResponseEntity writeBoard(Long artistId, ArtistBoardDto artistBoardDto){
        Artist artist = artistRepository.findByArtistId(artistId)
                .orElseThrow(IllegalStateException::new);
        ArtistBoard artistBoard = new ArtistBoard(artist, artistBoardDto);
        artistBoardRepository.save(artistBoard);

        return new ResponseEntity("ok", HttpStatus.OK);
    }

    public ResponseEntity updateBoard(ArtistBoardDto artistBoardDto){
        ArtistBoard artistBoard = artistBoardRepository.findById(artistBoardDto.getBoardId())
                .orElseThrow(IllegalStateException::new);
        artistBoard.setTitle(artistBoardDto.getTitle());
        artistBoard.setContent(artistBoardDto.getContent());
        artistBoardRepository.save(artistBoard);

        return new ResponseEntity("ok", HttpStatus.OK);
    }

    public List<ArtistBoardDto> getBoardList(Artist artist_id) {
        List<ArtistBoard> boards = artistBoardRepository.findByArtistId(artist_id);
        List<ArtistBoardDto> boardDtoList = new ArrayList<>();

        for(ArtistBoard board : boards){
            ArtistBoardDto boardDto = ArtistBoardDto.builder()
                    .boardId(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .build();

            boardDtoList.add(boardDto);
        }

        return boardDtoList;
    }
}
