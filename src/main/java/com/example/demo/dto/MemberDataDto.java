package com.example.demo.dto;

import com.example.demo.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDataDto {

    private String name;

    private String email;

    private String address;

    private String phoneNumber;

    private String account_name;

    private String account;

    private String image;

    private String role;


    public MemberDataDto(Member member){
        this.name = member.getName();
        this.email = member.getEmail();
        this.address = member.getAddress();
        this.phoneNumber = member.getPhoneNumber();
        this.account = member.getAccount();
        this.account_name = member.getAccount_name();
        this.image = member.getImage();
        this.role = member.getRole();
    }

    @Builder
    public MemberDataDto(String name, String address, String phoneNumber, String account,
                         String account_name, String image , String role) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.account = account;
        this.account_name = account_name;
        this.image = image;
        this.role = role;
    }

}
