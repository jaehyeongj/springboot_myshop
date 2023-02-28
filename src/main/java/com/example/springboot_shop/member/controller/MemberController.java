package com.example.springboot_shop.member.controller;


import com.example.springboot_shop.member.dto.*;
import com.example.springboot_shop.member.entity.Member;
import com.example.springboot_shop.member.service.FileUploadService;
import com.example.springboot_shop.member.service.MemberService;
import com.example.springboot_shop.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @Value("${file.dir}")
    private String fileDir;

    private final FileUploadService fileUploadService;

    @GetMapping("/id")
    public Long getMyId(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        return member.getId();
    }

    @GetMapping("/")
    public ProfileDto getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        return memberService.getMyProfile(member);
    }

    @GetMapping("/boards")
    public List<BoardPageDto.BoardContent> getMyBoardList(@PageableDefault Pageable pageable,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        return memberService.getMyBoards(member.getId(), pageable).getContents();
    }

    @GetMapping("/buddies")
    public List<ProfileDto> getMyBuddyList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        List<ProfileDto> myBuddies = memberService.getMyBuddies(member.getId());
        return myBuddies;
    }

    @GetMapping("/notYetBuddies")
    public List<BuddyRequestDto> getBuddyRequest(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        List<BuddyRequestDto> myBuddies = memberService.getBuddyRequests(member.getId());
        return myBuddies;
    }

    // 친구 신청
    @PatchMapping("/notYetBuddies/{userId}")
    public ResponseEntity<String> requestBuddy(@PathVariable Long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        return memberService.requestBuddy(member.getId(), userId);
    }

    // 친구 수락/거절
    @PostMapping("/notYetBuddies")
    public ResponseEntity<String> answerBuddyRequest(@RequestBody AnswerBuddyRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        return memberService.answerBuddyRequest(member.getId(), request.getRequestMemberId(),
                request.getAnswer());
    }

    @PostMapping("/")
    public ResponseEntity<String> changeMyProfile(
            @RequestPart("requestDto") UpdateProfileRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        Member member = userDetails.getMember();
        log.info("multipartFile={}", image);
        return memberService.changeMyProfile(member, request, image);
    }


    @GetMapping("/{memberId}")
    @ResponseBody
    public ProfileDto getOtherProfile(@PathVariable Long memberId) {
        return memberService.getOtherProfile(memberId);
    }

    @PostMapping("/mannerPoints")
    public ResponseEntity<String> changeMannerPoints(@RequestBody MannerPointsRequest request) {
        return memberService.changeMannerPoints(request);
    }

    // 친구 삭제
    @DeleteMapping("/buddies/{buddyId}")
    public ResponseEntity<String> deleteMyBuddy(@PathVariable Long buddyId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        return memberService.deleteMyBuddy(member.getId(), buddyId);
    }

    public static Pageable toPageable(Integer currentPage, Integer size) {
        return PageRequest.of((currentPage - 1), size);
    }
}