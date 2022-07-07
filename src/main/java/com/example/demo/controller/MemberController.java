package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.JwtTokenDto;
import com.example.demo.dto.MemberDataDto;
import com.example.demo.dto.MemberFormDto;
import com.example.demo.dto.RequestTokenDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.RefreshToken;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.service.MemberService;
import com.example.demo.service.MemberTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/members")
@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class MemberController {

    private final MemberTokenService memberTokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @GetMapping("/new")
    public ResponseEntity memberForm(Model model){
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity newMember(@Valid @RequestBody MemberFormDto memberFormDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity("binding error", HttpStatus.NOT_FOUND);
        }

        try {
            Member createMember = Member.createMember(memberFormDto, passwordEncoder);
            memberService.savedMember(createMember);
        } catch (IllegalStateException e){
            System.out.println(e.getMessage());
            return new ResponseEntity("IllegalStateException", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity("success", HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public JwtTokenDto login(@RequestBody Map<String, String> user) {
        Member member = memberRepository.findByEmail(user.get("email"));
        JwtTokenDto jwtTokenDto = new JwtTokenDto();
        if (!passwordEncoder.matches(user.get("pass"), member.getPass())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        RefreshToken oldRefreshToken = refreshTokenRepository.findByEmail(member.getEmail())
                .orElseThrow(IllegalStateException::new);
        jwtTokenDto.setAccessToken(jwtTokenProvider.createToken(member.getEmail(), member.getRole()));
        jwtTokenDto.setRefreshToken(jwtTokenProvider.createRefreshToken(member.getEmail(),member.getRole()));
        jwtTokenDto.setDate(jwtTokenProvider.jwtValidDate());
        RefreshToken refreshToken = new RefreshToken(user.get("email"), jwtTokenDto.getRefreshToken());
        oldRefreshToken.updateToken(refreshToken.getToken());
        refreshTokenRepository.save(oldRefreshToken);
        return jwtTokenDto;
    }

    @PostMapping("/login/id")
    public ResponseEntity duplicateId(@RequestBody Map<String, String> emailMap) throws NullPointerException{
        Member member = memberRepository.findByEmail(emailMap.get("email"));

        if(member == null) {
            return new ResponseEntity("OK", HttpStatus.OK);
        }

        return new ResponseEntity("No", HttpStatus.FORBIDDEN);


    }

    @PostMapping("/username")
    public MemberDataDto currentUserName(@RequestBody Map<String, String> emailMap) throws NullPointerException {
        Member member = memberRepository.findByEmail(emailMap.get("email"));
        MemberDataDto memberDataDto = new MemberDataDto();

        memberDataDto.setName(member.getName());
        memberDataDto.setAccount_name(member.getAccount_name());
        memberDataDto.setAccount(member.getAccount());
        memberDataDto.setAddress(member.getAddress());
        memberDataDto.setEmail(member.getEmail());
        memberDataDto.setPhoneNumber(member.getPhoneNumber());

        return memberDataDto;
    }

    @PostMapping("/reissue")
    public JwtTokenDto refreshToken(@RequestBody RequestTokenDto requestTokenDto) {
        JwtTokenDto jwtTokenDto = memberTokenService.reissue(requestTokenDto);

        return jwtTokenDto;
    }

}
