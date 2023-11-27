package com.example.demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${secretKey.value}")
    private String secretKey;

    private long tokenValidTime = 2 * 60 * 60 * 1000L; // 토큰 유효시간 2시간, 1000L는 1초
    private long refreshTokenValidTime = 24 * 60 * 60 * 1000L; // 토큰 유효시간 24시간

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); //객체 초기화, secret 키를 Base64로 인코딩
    }

    //JWT 토큰 생성
    public String createToken(String userPk, String roles) {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload에 저장되는 정보 단위
        claims.put("roles", roles); // 정보는 key /value 쌍으로 저장
        Date now = new Date(); //현재 시간
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // H256 알고리즘과 signature에 들어갈 secret 값 세팅
                .compact();
    }

    public String createRefreshToken(String userPk, String roles) {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload에 저장되는 정보 단위
        claims.put("roles", roles); // 정보는 key /value 쌍으로 저장
        Date now = new Date(); //현재 시간
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime)) // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // H256 알고리즘과 signature에 들어갈 secret 값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        // JWS = 서버에서 인증을 근거로 인증정보를 서버의 private key로 서명 한것을 토큰화 한것
        //getSubject = 비밀키로 토큰을 풀어 값을 가져옴 parseClaimsJws = 토큰을 jws로 파싱
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request Header에서 token 값을 가져옴 "X-AUTH-TOKEN" : "TOKEN값" (key/value 형식)
    public String resolveToken(HttpServletRequest request) {
//        return request.getHeader("X-AUTH-TOKEN");
        return request.getHeader("ACCESS-TOKEN");

    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date()); // 지금 시간과 만료 시간을 비교해 지금시간이 만료 시간을 넘어선다면 false리턴
        } catch (Exception e){
            return false;
        }
    }

    public Date jwtValidDate() {
        Date now = new Date();
        Date date = new Date(now.getTime() + tokenValidTime);
        return date;
    }
}
