package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.MemberFormDto;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequestMapping("/members")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MemberController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

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
    public String login(@RequestBody Map<String, String> user) {
        Member member = memberRepository.findByEmail(user.get("email"));
        if (!passwordEncoder.matches(user.get("pass"), member.getPass())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getEmail(), member.getRole());
    }

    @PostMapping("/login/id")
    public ResponseEntity duplicateId(@RequestBody Map<String, String> emailMap) throws NullPointerException{
        Member member = memberRepository.findByEmail(emailMap.get("email"));

        if(member == null) {
            return new ResponseEntity("OK", HttpStatus.OK);
        }

        return new ResponseEntity("No", HttpStatus.FORBIDDEN);


    }

}
