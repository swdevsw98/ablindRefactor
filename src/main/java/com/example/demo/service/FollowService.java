package com.example.demo.service;

import com.example.demo.entity.Artist;
import com.example.demo.entity.Follow;
import com.example.demo.entity.Member;
import com.example.demo.repository.ArtistFollowRepository;
import com.example.demo.repository.ArtistRepository;
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
    public ResponseEntity save(Long artist_id, String email){
        Artist followArtistId = artistRepository.findByArtistId(artist_id)
                .orElseThrow(IllegalStateException::new);
        Member followUserId = memberRepository.findByEmail(email)
                .orElseThrow(IllegalStateException::new);

        Follow follow = artistFollowRepository.findByFollowArtistIdAndFollowUserId(followArtistId, followUserId)
                .orElse(new Follow(followArtistId, followUserId));

        artistFollowRepository.save(follow);

        return new ResponseEntity<>("ok", HttpStatus.OK);
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
