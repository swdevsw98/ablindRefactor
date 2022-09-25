package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.admin.MainBannerDto;
import com.example.demo.entity.MainBanner;
import com.example.demo.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {
    private final JwtTokenProvider jwtTokenProvider;
    private final AdminService adminService;

    @PostMapping("/add/mainbanner")
    public ResponseEntity addMainBanner(@RequestPart(value="file", required = false)MultipartFile multipartFile,
                                        @RequestPart(value = "MainBannerDto")MainBannerDto mainBannerDto,
                                        ServletRequest request) throws IOException {
        return adminService.addMainBanner(getEmail(request), multipartFile, mainBannerDto);
    }

    @DeleteMapping("/delete/mainbanner")
    public ResponseEntity deleteMainBanner(@RequestBody MainBannerDto mainBannerDto,
                                           ServletRequest request){
        return adminService.deleteMainBanner(getEmail(request),mainBannerDto.getId());
    }

    private String getEmail(ServletRequest request){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        return jwtTokenProvider.getUserPk(token);
    }
}
