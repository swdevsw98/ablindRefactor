package com.example.demo.controller;

import com.example.demo.dto.member.MemberFormDto;
import com.example.demo.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
//@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;
/*
    public Member createMember(String email, String pass){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail(email);
        memberFormDto.setPass(pass);
        memberFormDto.setName("hsw");
        Member member = Member.createMember(memberFormDto);
        return memberService.savedMember(member);
    }
/*
    @Test
    public void 회원가입_get() throws Exception {
        mockMvc.perform(get("/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("new"))
                .andExpect(content().string(containsString("Email:")));
    }

 */

    @Test
    public void 회원가입_post() throws Exception {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName("hsw");
        memberFormDto.setEmail("abc@gmail.com");
        memberFormDto.setPass("1234");

        mockMvc.perform(post("/new"))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/"));
    }



}
