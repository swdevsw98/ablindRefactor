package com.example.demo.service.admin;

import com.example.demo.dto.artist.ArtWorkDto;
import com.example.demo.dto.artist.ArtistDetailDto;
import com.example.demo.dto.artist.ArtistInfoDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.artist.ArtWorks;
import com.example.demo.entity.artist.Artist;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.artist.ArtistRepository;
import com.example.demo.repository.artist.ArtistWorkRepository;
import com.example.demo.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminArtistService {

    private final S3Uploader s3Uploader;
    private final MemberRepository memberRepository;
    private final ArtistRepository artistRepository;
    private final ArtistWorkRepository artistWorkRepository;

    public ResponseEntity addArtist(String email, ArtistDetailDto artistDetailDto,
                                    MultipartFile profile, MultipartFile backGround,
                                    MultipartFile detail) throws IOException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());

        Artist artist = new Artist();
        artist.addArtist(artistDetailDto);

        String[] profiles = s3Uploader.upload(profile,"artist/profile");
        String[] backGrounds = s3Uploader.upload(backGround, "artist/backGround");
        String[] details = s3Uploader.upload(detail, "artist/detail");

        artist.setProfile(profiles[1]);
        artist.setDeleteProfile(profiles[0]);
        artist.setBackGround(backGrounds[1]);
        artist.setDeleteBackGround(backGrounds[0]);
        artist.setDetail(details[1]);
        artist.setDeleteDetail(details[0]);

        artistRepository.save(artist);
        return new ResponseEntity("작가 등록 성공", HttpStatus.OK);
    }

    public ResponseEntity deleteArtist(String email, ArtistDetailDto artistDetailDto){
        Artist artist = artistRepository.findById(artistDetailDto.getArtistId())
                .orElseThrow(()->new IllegalStateException("그런 아티스트 없음"));
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());

        s3Uploader.deleteFile(artist.getDeleteProfile());
        s3Uploader.deleteFile(artist.getDeleteBackGround());
        s3Uploader.deleteFile(artist.getDeleteDetail());
        artistRepository.deleteById(artistDetailDto.getArtistId());

        return new ResponseEntity("작가 삭제 성공", HttpStatus.OK);
    }

    /**
     * 작품 추가
     */
    public ResponseEntity addArtistWork(String email, Long artistId , MultipartFile work) throws IOException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new IllegalStateException("작가 먼저 생성해주세요"));

        String[] works = s3Uploader.upload(work, "artist/works");
        ArtWorks artWorks = new ArtWorks();
        artWorks.setWork(works[1]);
        artWorks.setDeleteWork(works[0]);
        artWorks.setArtistWorkId(artist);
        artist.getWorks().add(artWorks);

        artistRepository.save(artist);
        return new ResponseEntity("작품 등록 성공" , HttpStatus.OK);
    }

    /**
     * 작품 삭제
     */
    public ResponseEntity deleteArtWork(String email, ArtWorkDto artWorkDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());
        ArtWorks artWorks = artistWorkRepository.findById(artWorkDto.getId())
                .orElseThrow(() -> new IllegalStateException("그런 작품 없음"));
        s3Uploader.deleteFile(artWorks.getDeleteWork());
        artistWorkRepository.deleteById(artWorkDto.getId());

        return new ResponseEntity("작품 삭제", HttpStatus.OK);
    }

    private void isAdmin(String role) {
        if(role != "ADMIN") {
            new IllegalStateException("관리자 승인을 받으세요.");
        }
    }
}
