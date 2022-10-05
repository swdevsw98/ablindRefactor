package com.example.demo.service.admin;

import com.example.demo.dto.artist.FollowDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.artist.Follow;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.artist.ArtistFollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminArtistFollowService {

    private final MemberRepository memberRepository;
    private final ArtistFollowRepository followRepository;

    /**
     * follow list 출력
     */
    public List<FollowDto> listFollow(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());
        List<FollowDto> followDtos = new ArrayList<>();
        List<Follow> follows = followRepository.findAll();

        for(Follow follow : follows){
            FollowDto followDto = new FollowDto(follow);
            followDtos.add(followDto);
        }

        return followDtos;
    }

    /**
     * 구독 승인
     */
    public ResponseEntity approveFollow(String email, FollowDto followDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());
        Follow follow = followRepository.findById(followDto.getId())
                .orElseThrow(() -> new IllegalStateException("그런 구독 없음"));

        follow.setApprove(true);
        followRepository.save(follow);
        return new ResponseEntity("구독 승인 성공", HttpStatus.OK);
    }

    /**
     * 구독 거절
     */
    public ResponseEntity rejectFollow(String email, FollowDto followDto){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());
        followRepository.deleteById(followDto.getId());
        return new ResponseEntity("구독 거절 정상 삭제되었습니다.", HttpStatus.OK);
    }

    private void isAdmin(String role) {
        if(role != "ADMIN") {
            new IllegalStateException("관리자 승인을 받으세요.");
        }
    }
}
