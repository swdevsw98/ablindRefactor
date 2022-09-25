package com.example.demo.service.admin;

import com.example.demo.dto.admin.MainBannerDto;
import com.example.demo.entity.MainBanner;
import com.example.demo.entity.Member;
import com.example.demo.repository.MainBannerRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final S3Uploader s3Uploader;
    private final MemberRepository memberRepository;
    private final MainBannerRepository mainBannerRepository;

    public ResponseEntity addMainBanner(String email, MultipartFile multipartFile, MainBannerDto mainBannerDto) throws IOException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());

        String[] values = s3Uploader.upload(multipartFile, "mainBanner");

        MainBanner mainBanner = new MainBanner(values[1], values[0], mainBannerDto.getContent(), mainBannerDto.getLink());

        mainBannerRepository.save(mainBanner);

        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity deleteMainBanner(String email, Long id) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());

        MainBanner mainBanner = mainBannerRepository.findById(id)
                        .orElseThrow(() -> new IllegalStateException("없는 배너"));
        s3Uploader.deleteFile(mainBanner.getDeleteImage());
        mainBannerRepository.deleteById(mainBanner.getId());
        return new ResponseEntity(HttpStatus.OK);
    }
    private void isAdmin(String role) {
        if(role != "ADMIN") {
            new IllegalStateException("관리자 승인을 받으세요.");
        }
    }
}
