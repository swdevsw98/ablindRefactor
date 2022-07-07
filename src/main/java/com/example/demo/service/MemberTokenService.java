package com.example.demo.service;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.JwtTokenDto;
import com.example.demo.dto.RequestTokenDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.RefreshToken;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberTokenService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    @Transactional
    public JwtTokenDto reissue(RequestTokenDto requestTokenDto){
        //refresh token 만료
        if(!jwtTokenProvider.validateToken(requestTokenDto.getRefreshToken())){
            throw new IllegalStateException("refresh error");
        }

        //access token에서 PK 가져오기
        String accessToken = requestTokenDto.getAccessToken();
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        //user pk로 유저 검색/ repo에 저장된 토큰 있는지 검색
        Member member = memberRepository.findByEmail(authentication.getName());
        if (member == null)
        {
            throw new IllegalStateException("not find member");
        }
        RefreshToken refreshToken = refreshTokenRepository.findByEmail(member.getEmail())
                .orElseThrow(IllegalStateException::new);

        if(!refreshToken.getToken().equals(requestTokenDto.getRefreshToken()))
            throw new IllegalStateException("not equal refresh");

        //토큰 재발급 및 리프레쉬 토큰 저장
        JwtTokenDto newCreatedToken = new JwtTokenDto();
        newCreatedToken.setAccessToken(jwtTokenProvider.createToken(member.getEmail(), member.getRole()));
        newCreatedToken.setRefreshToken(jwtTokenProvider.createRefreshToken(member.getEmail(), member.getRole()));
        newCreatedToken.setDate(jwtTokenProvider.jwtValidDate());
        RefreshToken updateRefreshToken = refreshToken.updateToken(newCreatedToken.getRefreshToken());
        refreshTokenRepository.save(updateRefreshToken);

        return newCreatedToken;
    }
}
