package com.example.demo.dto;

import com.example.demo.entity.Member;
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

    public MemberDataDto(Member member){
        this.name = member.getName();
        this.email = member.getEmail();
        this.address = member.getAddress();
        this.phoneNumber = member.getPhoneNumber();
        this.account = member.getAccount();
        this.account_name = member.getAccount_name();
    }
}
