package com.example.demo.controller;

import com.example.demo.dto.artist.ArtistBoardDto;
import com.example.demo.dto.artist.ArtistDetailDto;
import com.example.demo.dto.artist.ArtistInfoDto;
import com.example.demo.entity.artist.ArtWorks;
import com.example.demo.entity.artist.Artist;
import com.example.demo.repository.artist.ArtistBoardRepository;
import com.example.demo.repository.artist.ArtistRepository;
import com.example.demo.repository.artist.ArtistWorkRepository;
import com.example.demo.service.artist.ArtistBoardService;
import com.example.demo.service.artist.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RequestMapping("/artist")
@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistRepository artistRepository;
    private final ArtistBoardRepository artistBoardRepository;
    private final ArtistBoardService artistBoardService;
    private final FollowService followService;
    private final ArtistWorkRepository artistWorkRepository;

    //artist 개인페이지
    @GetMapping("")
    public List<ArtistInfoDto> privateArtist() throws NullPointerException {
        List<Artist> artists = artistRepository.findAll();
        List<ArtistInfoDto> artistInfoDtoList = new ArrayList<>();

        for(Artist artist : artists) {
            ArtistInfoDto artistInfoDto = ArtistInfoDto.builder()
                    .artist(artist)
                    .build();

            artistInfoDtoList.add(artistInfoDto);
        }
        return artistInfoDtoList;
    }

    @GetMapping("/{artistId}")
    public ArtistDetailDto detailArtistList(@PathVariable(name="artistId") Long artist_id){
            Artist artist = artistRepository.findByArtistId(artist_id)
                    .orElseThrow(() -> new IllegalStateException("없는 작가입니다."));
            ArtistDetailDto artistDetailDto = new ArtistDetailDto(artist);
            List<ArtWorks> works = artistWorkRepository.findAllByArtistWorkId(artist)
                    .orElseThrow(() -> new IllegalStateException("없는 작품"));

            for (ArtWorks work : works){
                artistDetailDto.add(work);
            }

        return artistDetailDto;
    }


//    응원 리스트 출력
    @GetMapping("/{artistId}/board")
    @ResponseBody
    public List<ArtistBoardDto> listBoard(@PathVariable(name = "artistId") Artist artist_id){
        List<ArtistBoardDto> list = artistBoardService.getBoardList(artist_id);

        return list;
    }

    //응원글 작성
    @PostMapping("/{artistId}/board")
    public ResponseEntity writeBoard(@PathVariable(name = "artistId") Long artist_id, @RequestBody Map<String, String> Board){
        Artist artist = artistRepository.findByArtistId(artist_id)
                .orElseThrow(IllegalStateException::new);
        ArtistBoardDto artistBoardDto = new ArtistBoardDto(Board.get("title"), Board.get("content"));
        artistBoardService.writeBoard(artist.getArtistId(), artistBoardDto);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    //응원글 수정
    @PutMapping("/{artistId}/board/update")
    public ResponseEntity updateBoard(@RequestBody ArtistBoardDto artistBoardDto){
        artistBoardService.updateBoard(artistBoardDto);

        return new ResponseEntity("update success", HttpStatus.OK);
    }

    //구독기능
    @PostMapping("/{artistId}/follow/{price}")
    public ResponseEntity followArtist(@PathVariable(name = "artistId") Long artist_id, @RequestBody Map<String, String> emailMap,
                                       @PathVariable(name = "price") Long price){
        followService.save(artist_id, emailMap.get("email"), price);

        return new ResponseEntity<>("follow", HttpStatus.OK);
    }

    //구독 확인 기능
    @GetMapping("/{artistId}/follow")
    public ResponseEntity followCheck(@PathVariable(name = "artistId") Long artist_id, @RequestBody Map<String, String> email){
        followService.findByArtistIdAndUserId(artist_id, email.get("email"));

        return new ResponseEntity("구독했어영 ㅋ", HttpStatus.OK);
    }


    //구독 취소 기능
    @DeleteMapping("/{artistId}/unfollow")
    public ResponseEntity unFollowArtist(@PathVariable(name = "artistId") Long artist_id, @RequestBody Map<String, String> emailMap) {
        followService.delete(artist_id, emailMap.get("email"));
        return new ResponseEntity("unFollow", HttpStatus.OK);
    }
}
