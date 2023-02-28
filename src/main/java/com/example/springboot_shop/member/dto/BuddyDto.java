package com.example.springboot_shop.member.dto;


import com.example.springboot_shop.member.entity.Member;
import com.example.springboot_shop.member.entity.Profile;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BuddyDto {

    private Profile profile;
    private String email;

    public BuddyDto(Member member) {
        this.profile = member.getProfile();
        this.email = member.getEmail();
    }

    public static List<BuddyDto> of(List<Member> members) {
        return members.stream().map(BuddyDto::new).collect(Collectors.toList());
    }
}
