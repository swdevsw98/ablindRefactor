package com.example.demo.dto.member;

import lombok.Data;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Data
public class MemberFormDto {

    private String name;

    private String pass;

    private String email;

    private String address;

    private String phoneNumber;

    private String account_name;

    private String account;
}
