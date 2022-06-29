package com.example.demo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Data
public class MemberFormDto {

    @NotBlank(message = "이름은 필수 값입니다.")
    private String name;

    @NotBlank(message = "비밀번호는 필수 값입니다.")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String pass;

    @NotBlank(message = "이메일은 필수 값입니다.")
    @Email(message = "이메일 형식으로 써주세요.")
    private String email;

    @NotEmpty(message = "주소는 필수 값입니다.")
    private String address;

    @NotEmpty(message = "전화번호는 필수 값입니다.")
    private String phoneNumber;

    @NotEmpty(message = "은행 이름을 적어주세요.")
    private String account_name;

    @NotEmpty(message = "계좌번호를 적어주세요.")
    private String account;
}
