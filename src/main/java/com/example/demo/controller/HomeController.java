package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.RequestTokenDto;
import com.example.demo.entity.MainBanner;
import com.example.demo.repository.MainBannerRepository;
import com.example.demo.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOError;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final JwtTokenProvider jwtTokenProvider;
    private final S3Uploader s3Uploader;
    private final MainBannerRepository mainBannerRepository;


    @GetMapping("/banner")
    public List<MainBanner> listMainBanner() { return mainBannerRepository.findAll(); }

    @PostMapping("/image")
    public ResponseEntity upload(@RequestParam("images") MultipartFile file) throws IOException {
        s3Uploader.upload(file, "static");
        return new ResponseEntity(HttpStatus.OK);
    }

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
