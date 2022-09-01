package com.example.demo.entity;

import com.example.demo.config.SecurityConfig;
import com.example.demo.dto.MemberFormDto;
import com.example.demo.entity.artist.Follow;
import com.example.demo.entity.cart.Cart;
import com.example.demo.entity.shop.ItemQnaBoard;
import com.example.demo.entity.shop.ItemReviewBoard;
import com.example.demo.entity.shop.Order;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@ToString
@Data
@Table(name = "member")
@Entity
public class Member {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String pass;

    @Column(unique = true) //컬럼 값에 동일값은 못들어감.
    private String email;

    private String address;

//    @Enumerated(EnumType.STRING)
    private String role;

    private String phoneNumber;

    private String account_name;

    private String account;

    private Long artistRoleId;

    private String image;
    private String deleteImage;

    @OneToOne
    private Cart cartId;

    @OneToOne
    private ItemQnaBoard itemQnaBoard;

    @OneToOne
    private ItemReviewBoard itemReviewBoard;

    @OneToMany(mappedBy = "memberId")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "followUserId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> artistFollow = new ArrayList<>();

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        String password = passwordEncoder.encode(memberFormDto.getPass());
        member.setPass(password);
        member.setEmail(memberFormDto.getEmail());
        member.setRole("USER");
        member.setAddress(memberFormDto.getAddress());
        member.setPhoneNumber(memberFormDto.getPhoneNumber());
        member.setAccount_name(memberFormDto.getAccount_name());
        member.setAccount(memberFormDto.getAccount());
        return member;
    }

    public void updateProfile(String image, String deleteImage){
        this.image = image;
        this.deleteImage = deleteImage;
    }

    public void updateMemberInfo(MemberFormDto memberFormDto){
        this.name = memberFormDto.getName();
        this.address = memberFormDto.getAddress();
        this.phoneNumber = memberFormDto.getPhoneNumber();
        this.account = memberFormDto.getAccount();
        this.account_name = memberFormDto.getAccount_name();
    }

    public void updatePassword(String pass, PasswordEncoder passwordEncoder){
        this.pass = passwordEncoder.encode(pass);
    }

}
