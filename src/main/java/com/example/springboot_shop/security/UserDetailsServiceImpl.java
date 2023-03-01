package com.example.springboot_shop.security;

import com.example.springboot_shop.exception.NotFoundException;
import com.example.springboot_shop.member.entity.Member;
import com.example.springboot_shop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        var member = memberRepository.findByEmail(email)
                .orElseThrow(NotFoundException.NotFoundMemberException::new);
        return new UserDetailsImpl(member, member.getEmail(), member.getPassword());
    }

    public Member loadMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(NotFoundException.NotFoundMemberException::new);
    }

}
