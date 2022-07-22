package com.example.demo.service.artist;

import com.example.demo.entity.artist.Artist;
import com.example.demo.entity.artist.Follow;
import com.example.demo.entity.Member;
import com.example.demo.repository.artist.ArtistFollowRepository;
import com.example.demo.repository.artist.ArtistRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

    private final ArtistRepository artistRepository;
    private final MemberRepository memberRepository;
    private final ArtistFollowRepository artistFollowRepository;

    //중복처리 해야함
    public ResponseEntity save(Long artist_id, String email, Long price){
        Artist followArtistId = artistRepository.findByArtistId(artist_id)
                .orElseThrow(IllegalStateException::new);
        Member followUserId = memberRepository.findByEmail(email)
                .orElseThrow(IllegalStateException::new);

        Follow follow = artistFollowRepository.findByFollowArtistIdAndFollowUserId(followArtistId, followUserId)
                .orElse(new Follow(followArtistId, followUserId, price));


        artistFollowRepository.save(follow);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    //확인하는 로직
    public Follow findByArtistIdAndUserId(Long artist_id, String email){
        Artist followArtistId = artistRepository.findByArtistId(artist_id)
                .orElseThrow(IllegalStateException::new);
        Member followUserId = memberRepository.findByEmail(email)
                .orElseThrow(IllegalStateException::new);

        Follow follow = artistFollowRepository.findByFollowArtistIdAndFollowUserId(followArtistId, followUserId)
                .orElseThrow(() -> new IllegalStateException("구독 안하셨습니다."));
        return follow;
    }


    public ResponseEntity delete(Long artist_id, String email){
        Artist followArtistId = artistRepository.findByArtistId(artist_id)
                .orElseThrow(IllegalStateException::new);
        Member followUserId = memberRepository.findByEmail(email)
                .orElseThrow(IllegalStateException::new);

        Follow follow = artistFollowRepository.findByFollowArtistIdAndFollowUserId(followArtistId, followUserId)
                .orElseThrow(IllegalStateException::new);

        artistFollowRepository.deleteById(follow.getId());
        return  new ResponseEntity("ok", HttpStatus.OK);
    }
}
