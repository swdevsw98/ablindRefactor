package com.example.demo.service.admin;

import com.example.demo.dto.MemberDataDto;
import com.example.demo.dto.admin.MainBannerDto;
import com.example.demo.dto.shop.ShopBannerDto;
import com.example.demo.entity.MainBanner;
import com.example.demo.entity.Member;
import com.example.demo.entity.shop.ShopBanner;
import com.example.demo.repository.MainBannerRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.shop.ItemQnaRepository;
import com.example.demo.repository.shop.ItemReviewRepository;
import com.example.demo.repository.shop.OrderRepository;
import com.example.demo.repository.shop.ShopBannerRepository;
import com.example.demo.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final S3Uploader s3Uploader;
    private final MemberRepository memberRepository;
    private final MainBannerRepository mainBannerRepository;
    private final ItemQnaRepository itemQnaRepository;
    private final OrderRepository orderRepository;
    private final ItemReviewRepository itemReviewRepository;
    private final ShopBannerRepository shopBannerRepository;

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

    public ResponseEntity addShopBanner(String email, MultipartFile multipartFile, ShopBannerDto shopBannerDto) throws IOException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());

        String[] values = s3Uploader.upload(multipartFile, "shopBanner");

        ShopBanner shopBanner = new ShopBanner(values[1], values[0], shopBannerDto.getContent(), shopBannerDto.getLink());

        shopBannerRepository.save(shopBanner);

        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity deleteShopBanner(String email, Long id) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());

        ShopBanner shopBanner = shopBannerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("없는 배너"));
        s3Uploader.deleteFile(shopBanner.getDeleteImage());
        shopBannerRepository.deleteById(shopBanner.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 유저 리스트
     */
    public List<MemberDataDto> listMember(String email){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());
        List<MemberDataDto> memberDataDtos = new ArrayList<>();
        List<Member> members = memberRepository.findAll();

        for(Member member1 : members){
            MemberDataDto memberDataDto = new MemberDataDto(member1);

            memberDataDtos.add(memberDataDto);
        }

        return memberDataDtos;
    }

    /**
     * 회원 탈퇴
     */
    public ResponseEntity deleteMember(String email, MemberDataDto memberDataDto){
        //admin
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());
        //user
        Member user = memberRepository.findById(memberDataDto.getId())
                .orElseThrow(() -> new IllegalStateException("유저 없음"));
        if(user.getDeleteImage() != null)
            s3Uploader.deleteFile(member.getDeleteImage());

        itemQnaRepository.deleteAllByMember(user);
        orderRepository.deleteAllByMemberId(user);
        itemReviewRepository.deleteAllByMember(user);

        memberRepository.deleteById(memberDataDto.getId());

        return new ResponseEntity("유저 탈퇴 시킴", HttpStatus.OK);
    }

    private void isAdmin(String role) {
        if(role != "ADMIN") {
            new IllegalStateException("관리자 승인을 받으세요.");
        }
    }
}
