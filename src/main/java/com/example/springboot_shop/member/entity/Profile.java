package com.example.springboot_shop.member.entity;


import com.example.springboot_shop.exception.SignException;
import com.example.springboot_shop.member.domain.GameType;
import com.example.springboot_shop.member.domain.Tier;
import com.example.springboot_shop.member.dto.UpdateProfileRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    @Column
    private String nickname;

    @Column
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Tier tier;

    @Enumerated(EnumType.STRING)
    private GameType game;

    private Integer mannerPoints = 0;

    @Builder
    public Profile(String nickname, String profileImage, Tier tier, GameType game) {
        if (Pattern.matches("\\w{2,8}", nickname)) {
            this.nickname = nickname;
        } else {
            throw new SignException.InvalidNickname();
        }

        this.profileImage = profileImage;

        if (Tier.isContains(tier)) {
            this.tier = tier;
        }
        if (GameType.isContains(game)) {
            this.game = game;
        }
    }

    public void changeProfile(UpdateProfileRequest profileRequest, String imageDir) {
        if (!imageDir.isEmpty()) {
            this.profileImage = imageDir;
        }
        if (!profileRequest.getNickname().isEmpty()) {
            this.nickname = profileRequest.getNickname();
        }
        if (profileRequest.getGame() != null) {
            this.game = profileRequest.getGame();
        }
        if (profileRequest.getTier() != null) {
            this.tier = profileRequest.getTier();
        }
    }

    public void changeMannerPoints(String UpDown) {
        if (UpDown.equals("UP")) {
            this.mannerPoints += 10;
        } else if (UpDown.equals("DOWN")) {
            this.mannerPoints -= 10;
        }

    }
}
