package com.example.springboot_shop.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerBuddyRequestDto {

    private Long requestMemberId;
    private Boolean answer;
}
