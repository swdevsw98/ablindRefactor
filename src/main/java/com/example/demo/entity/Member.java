package com.example.demo.entity;

import com.example.demo.constant.Role;
import com.example.demo.dto.MemberFormDto;
import lombok.Data;

import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;


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


}
