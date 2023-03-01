package com.example.springboot_shop.member.dto;


import com.example.springboot_shop.member.domain.GameType;
import com.example.springboot_shop.member.domain.Tier;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UpdateProfileRequest {

    private String nickname;
    private Tier tier;
    private GameType game;

    public UpdateProfileRequest(String nickname, Tier tier, GameType game) {
        this.nickname = nickname;
        this.tier = tier;
        this.game = game;
    }

}
