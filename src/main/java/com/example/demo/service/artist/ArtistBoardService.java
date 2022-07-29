package com.example.demo.service.artist;

import com.example.demo.dto.artist.ArtistBoardDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.artist.Artist;
import com.example.demo.entity.artist.ArtistBoard;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.artist.ArtistBoardRepository;
import com.example.demo.repository.artist.ArtistRepository;
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
    private final MemberRepository memberRepository;

    public ResponseEntity writeBoard(Long artistId, ArtistBoardDto artistBoardDto){
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(IllegalStateException::new);
        Member member = memberRepository.findByEmail(artistBoardDto.getEmail())
                .orElseThrow(() -> new IllegalStateException("없는고객"));
        artistBoardDto.setWriter(member.getName());

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
                    .writer(board.getWriter())
                    .email(board.getEmail())
                    .createdAt(board.getCreatedAt())
                    .updatedAt(board.getUpdatedAt())
                    .build();

            boardDtoList.add(boardDto);
        }

        return boardDtoList;
    }

    public void deleteBoard(ArtistBoardDto artistBoardDto) {
        artistBoardRepository.deleteById(artistBoardDto.getBoardId());
    }
}
