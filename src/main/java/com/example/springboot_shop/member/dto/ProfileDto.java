package com.example.springboot_shop.member.dto;


import com.example.springboot_shop.member.domain.GameType;
import com.example.springboot_shop.member.domain.Tier;
import com.example.springboot_shop.member.entity.Member;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProfileDto {

    private Long id;
    private String nickname;
    private String image;
    private GameType game;
    private Tier tier;
    private int mannerPoints;
    private String email;

    public ProfileDto(Member member) {
        this.nickname = member.getProfile().getNickname();
        this.image = member.getProfile().getProfileImage();
        this.game = member.getProfile().getGame();
        this.tier = member.getProfile().getTier();
        this.mannerPoints = member.getProfile().getMannerPoints();
        this.email = member.getEmail();
        this.id = member.getId();
    }

    public static List<ProfileDto> of(List<Member> members) {
        return members.stream().map(ProfileDto::new).collect(Collectors.toList());
    }
}
