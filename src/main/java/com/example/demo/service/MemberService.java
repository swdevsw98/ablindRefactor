package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member savedMember(Member member){
        validateMember(member);
        return memberRepository.save(member); //save하면 entity가 리턴
    }

    public void validateMember(Member member){
        Member findMember = memberRepository.findByEmail(member.getEmail())
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(IllegalStateException::new);


        return User.builder() //필요한 데이터만 설정
                .username(member.getEmail())
                .password(member.getPass())
                .roles(member.getRole().toString())
                .build();
    }


}
