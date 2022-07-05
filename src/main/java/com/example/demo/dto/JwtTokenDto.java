package com.example.demo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class JwtTokenDto {

    private String jwtToken;

    private Date date;
}
