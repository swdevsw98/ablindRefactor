package com.example.demo.controller;

import com.example.demo.dto.MemberFormDto;
import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/members")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MemberController {

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
//
//    @GetMapping("/login")
//    public String memberLogin(){
//        return  "login";
//    }
//
//    @GetMapping("/login/error")
//    public String loginError(Model model){
//        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
//        return "login";
//    }
}
