package com.example.springboot_shop.member.service;


import com.example.springboot_shop.member.dto.*;
import com.example.springboot_shop.member.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MemberService {

    ProfileDto getMyProfile(Member member);

    BoardPageDto getMyBoards(Long memberId, Pageable pageable);

    List<ProfileDto> getMyBuddies(Long memberId);

    List<BuddyRequestDto> getBuddyRequests(Long memberId);

    ResponseEntity<String> changeMyProfile(Member member, UpdateProfileRequest request,
            MultipartFile image) throws IOException;

    ProfileDto getOtherProfile(Long memberId);

    ResponseEntity<String> requestBuddy(Long memberId, Long targetUserId);

    ResponseEntity<String> answerBuddyRequest(Long memberId, Long requestUserId, Boolean answer);

    ResponseEntity<String> changeMannerPoints(MannerPointsRequest request);

    Member responseMemberByMemberId(Long memberId); //TODO: memberEmail로 바꿀것

    ResponseEntity<String> deleteMyBuddy(Long memberId, Long buddyId);
}
