package com.example.demo.service.mypage;

import com.example.demo.dto.MemberDataDto;
import com.example.demo.dto.MemberFormDto;
import com.example.demo.dto.artist.ArtistDetailDto;
import com.example.demo.dto.order.MypageOrderItemDto;
import com.example.demo.dto.order.OrderDetailDto;
import com.example.demo.dto.order.OrderListDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.artist.Follow;
import com.example.demo.entity.shop.Order;
import com.example.demo.entity.shop.OrderItem;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.artist.ArtistFollowRepository;
import com.example.demo.repository.shop.OrderRepository;
import com.example.demo.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MypageService {


    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ArtistFollowRepository artistFollowRepository;
    private final S3Uploader s3Uploader;
    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 정보 가져오기
     */
    public MemberDataDto getMemberData(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("없는 유저"));

        MemberDataDto memberDataDto = MemberDataDto.builder()
                .name(member.getName())
                .address(member.getAddress())
                .image(member.getImage())
                .role(member.getRole())
                .account(member.getAccount())
                .account_name(member.getAccount_name())
                .phoneNumber(member.getPhoneNumber())
                .build();

        return memberDataDto;
    }

    /**
     * 팔로우한 작가 정보 가져오기
     */
    public List<ArtistDetailDto> getFollowArtist(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));
        List<Follow> artists = member.getArtistFollow();
        List<ArtistDetailDto> detailDtos = new ArrayList<>();

        for(Follow artist : artists){
            ArtistDetailDto detailDto = ArtistDetailDto.builder()
                    .artistId(artist.getFollowArtistId().getId())
                    .profile(artist.getFollowArtistId().getProfile())
                    .name(artist.getFollowArtistId().getName())
                    .intro(artist.getFollowArtistId().getIntro())
                    .subTitle(artist.getFollowArtistId().getSubTitle())
                    .content(artist.getFollowArtistId().getContent())
                    .email(artist.getFollowArtistId().getEmail())
                    .price(artist.getPrice())
                    .build();

            detailDtos.add(detailDto);
        }
        return detailDtos;
    }

    /**
     * 주문내역 조회
     */
    public List<OrderListDto> getOrderList(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));
        List<OrderListDto> orderListDtos = new ArrayList<>();
        List<Order> orders = orderRepository.findByMemberId(member)
                .orElseThrow(() -> new IllegalStateException("주문 내역이 없음"));

        for(Order order : orders){
            OrderListDto orderListDto = OrderListDto.builder()
                    .orderStatus(order.getOrderStatus())
                    .price(order.getPrice())
                    .createdAt(order.getCreatedAt())
                    .id(order.getId())
                    .build();

            for(OrderItem orderItem : order.getOrderItems()){
                MypageOrderItemDto mypageOrderItemDto = new MypageOrderItemDto(orderItem);
                orderListDto.getOrderItems().add(mypageOrderItemDto);
            }

            orderListDtos.add(orderListDto);
        }

        return orderListDtos;
    }

    /**
     * 주문상세 내역 조회
     */
    public OrderDetailDto getOrderDetail(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("없는 주문"));

        OrderDetailDto orderDetailDto = OrderDetailDto.builder()
                .orderStatus(order.getOrderStatus())
                .price(order.getPrice())
                .createdAt(order.getCreatedAt())
                .id(order.getId())
                .ordererName(order.getOrdererName())
                .ordererAccount(order.getOrdererAccount())
                .ordererAccountName(order.getOrdererAccountName())
                .ordererPhoneNumber(order.getOrdererPhoneNumber())
                .recipientAddress(order.getRecipientAddress())
                .recipientName(order.getRecipientName())
                .recipientPhoneNumber(order.getRecipientPhoneNumber())
                .build();

        for(OrderItem orderItem : order.getOrderItems()) {
            orderDetailDto.getOrderItems().add(orderItem);
        }

        return orderDetailDto;
    }

    /**
     * 프로필 수정
     */
    public ResponseEntity updateProfile(String email, MultipartFile multipartFile) throws IOException{
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));
        String[] values = s3Uploader.upload(multipartFile, "profile");
        if(member.getImage() != null) {
            s3Uploader.deleteFile(member.getDeleteImage());
        }
        member.updateProfile(values[1], values[0]);

        memberRepository.save(member);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 회원 정보 수정
     */
    public ResponseEntity updateMemberInfo(String email, MemberFormDto memberFormDto){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("그런 멤버 없음"));

        if(memberFormDto.getPass() != null){
            member.updatePassword(memberFormDto.getPass(), passwordEncoder);
        }

        member.updateMemberInfo(memberFormDto);
        memberRepository.save(member);

        return new ResponseEntity(HttpStatus.OK);
    }
}
