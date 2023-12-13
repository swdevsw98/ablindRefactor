package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.artist.ArtistBoardDto;
import com.example.demo.dto.artist.ArtistDetailDto;
import com.example.demo.dto.artist.ArtistInfoDto;
import com.example.demo.dto.artist.CommentDto;
import com.example.demo.dto.shop.ArtistProductRequest;
import com.example.demo.dto.shop.ProductsResponse;
import com.example.demo.entity.artist.ArtWorks;
import com.example.demo.entity.artist.Artist;
import com.example.demo.repository.artist.ArtistBoardRepository;
import com.example.demo.repository.artist.ArtistRepository;
import com.example.demo.repository.artist.ArtistWorkRepository;
import com.example.demo.service.MemberService;
import com.example.demo.service.artist.ArtistBoardCommentService;
import com.example.demo.service.artist.ArtistBoardService;
import com.example.demo.service.artist.FollowService;
import com.example.demo.service.shop.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RequestMapping("/artist")
@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ArtistRepository artistRepository;
    private final ArtistBoardRepository artistBoardRepository;
    private final ArtistBoardService artistBoardService;
    private final FollowService followService;
    private final ArtistWorkRepository artistWorkRepository;
    private final ArtistBoardCommentService artistBoardCommentService;
    private final MemberService memberService;
    private final ShopService shopService;

    //artist 작가 리스트 출력
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

    //작가 개인페이지
    @GetMapping("/{artistId}")
    public ArtistDetailDto detailArtistList(@PathVariable(name="artistId") Long artist_id){
            Artist artist = artistRepository.findById(artist_id)
                    .orElseThrow(() -> new IllegalStateException("없는 작가입니다."));
            ArtistDetailDto artistDetailDto = new ArtistDetailDto(artist);
            List<ArtWorks> works = artistWorkRepository.findAllByArtistWorkId(artist)
                    .orElseThrow(() -> new IllegalStateException("없는 작품"));

            for (ArtWorks work : works){
                artistDetailDto.add(work);
            }

        return artistDetailDto;
    }

    //작가확인
    @GetMapping("/{artistId}/check")
    public ResponseEntity checkArtist(@PathVariable(name = "artistId") Long artistId, ServletRequest request){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        return memberService.checkArtist(artistId, jwtTokenProvider.getUserPk(token));
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
//        Artist artist = artistRepository.findByArtistId(artist_id)
//                .orElseThrow(IllegalStateException::new);
        ArtistBoardDto artistBoardDto = ArtistBoardDto.builder()
                        .title(Board.get("title"))
                        .content(Board.get("content"))
                        .email(Board.get("email"))
                        .build();

        artistBoardService.writeBoard(artist_id, artistBoardDto);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    //응원글 수정
    @PutMapping("/{artistId}/board/update")
    public ResponseEntity updateBoard(@RequestBody ArtistBoardDto artistBoardDto){
        artistBoardService.updateBoard(artistBoardDto);

        return new ResponseEntity("update success", HttpStatus.OK);
    }

    //응원글 삭제
    @DeleteMapping("/{artistId}/board/delete")
    public ResponseEntity deleteBoard(@RequestBody ArtistBoardDto artistBoardDto){
        artistBoardService.deleteBoard(artistBoardDto);

        return new ResponseEntity("delete success" , HttpStatus.OK);
    }

    //구독기능
    @PostMapping("/{artistId}/follow/{price}")
    public ResponseEntity followArtist(@PathVariable(name = "artistId") Long artist_id, @RequestBody Map<String, String> emailMap,
                                       @PathVariable(name = "price") Long price){
        followService.save(artist_id, emailMap.get("email"), price);

        return new ResponseEntity<>("follow", HttpStatus.OK);
    }

    //구독 확인 기능
    @PostMapping("/{artistId}/follow")
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

    //해당 게시글 댓글 불러오기
    @GetMapping("/{artistId}/board/{boardId}")
    public List<CommentDto> getBoardComment(@PathVariable(name = "boardId") Long boardId){
        return artistBoardCommentService.getComment(boardId);
    }

    //댓글
    @PostMapping("/{artistId}/board/{boardId}")
    public ResponseEntity writeBoardComment(ServletRequest request,@PathVariable(name = "boardId") Long boardId,
                                            @RequestBody CommentDto commentDto) {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        artistBoardCommentService.writeComment(jwtTokenProvider.getUserPk(token), boardId, commentDto.getContent());
        return new ResponseEntity(HttpStatus.OK);
    }

    //댓글 수정
    @PutMapping("/{artistId}/board/{boardId}/update")
    public ResponseEntity updateBoardComment(@RequestBody CommentDto commentDto){
        return artistBoardCommentService.updateComment(commentDto);
    }

    //댓글 삭제
    @DeleteMapping("/{artistId}/board/{boardId}/delete")
    public ResponseEntity deleteBoardComment(@RequestBody CommentDto commentDto) {
        return artistBoardCommentService.deleteComment(commentDto.getCommentId());
    }

    //상품 등록
    @PostMapping("add/product")
    public ResponseEntity addProduct(
            @RequestPart(name = "artistProductRequest") ArtistProductRequest artistProductRequest,
            @RequestPart(name = "capturedImage") MultipartFile image,
            ServletRequest request) throws IOException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        String email = jwtTokenProvider.getUserPk(token);
        return shopService.addArtistProduct(artistProductRequest, email, image);
    }

    //상품 조회
    @GetMapping("products")
    public List<ProductsResponse> getProducts() {
        return shopService.getProducts();
    }
}
