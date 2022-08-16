package com.example.demo.service.mypage;

import com.example.demo.dto.MemberDataDto;
import com.example.demo.dto.artist.ArtistDetailDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.artist.Artist;
import com.example.demo.entity.artist.Follow;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.artist.ArtistFollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MypageService {


    private final MemberRepository memberRepository;
    private final ArtistFollowRepository artistFollowRepository;

    /**
     * 유저 정보 가져오기
     */
    public MemberDataDto getMemberData(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("없는 유저"));

        MemberDataDto memberDataDto = MemberDataDto.builder()
                .name(member.getName())
                .address(member.getAddress())
                .image(member.getImage())
                .role(member.getRole())
                .account(member.getAccount())
                .account_name(member.getAccount_name())
                .phoneNumber(member.getPhoneNumber())
                .build();

        return memberDataDto;
    }

    /**
     * 팔로우한 작가 정보 가져오기
     */
    public List<ArtistDetailDto> getFollowArtist(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));
        List<Follow> artists = member.getArtistFollow();
        List<ArtistDetailDto> detailDtos = new ArrayList<>();

        for(Follow artist : artists){
            ArtistDetailDto detailDto = ArtistDetailDto.builder()
                    .artistId(artist.getFollowArtistId().getId())
                    .profile(artist.getFollowArtistId().getProfile())
                    .name(artist.getFollowArtistId().getName())
                    .intro(artist.getFollowArtistId().getIntro())
                    .subTitle(artist.getFollowArtistId().getSubTitle())
                    .content(artist.getFollowArtistId().getContent())
                    .email(artist.getFollowArtistId().getEmail())
                    .build();

            detailDtos.add(detailDto);
        }
        return detailDtos;
    }
}
