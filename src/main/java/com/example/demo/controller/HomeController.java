package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.RequestTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/")
    public ResponseEntity home() {
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity admin() { return new ResponseEntity("success", HttpStatus.OK); }


    //토큰에서 이메일 추출
    @GetMapping("/user")
    public ResponseEntity user(ServletRequest request) {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        return new ResponseEntity("success", HttpStatus.OK);
    }
}
