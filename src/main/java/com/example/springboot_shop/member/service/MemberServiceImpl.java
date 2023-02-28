package com.example.springboot_shop.member.service;


import com.example.springboot_shop.board.entity.Board;
import com.example.springboot_shop.board.repository.BoardRepository;
import com.example.springboot_shop.exception.NotFoundException;
import com.example.springboot_shop.member.domain.FileDetail;
import com.example.springboot_shop.member.dto.*;
import com.example.springboot_shop.member.entity.Member;
import com.example.springboot_shop.member.entity.Profile;
import com.example.springboot_shop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final FileUploadService fileUploadService;

    @Override
    public ProfileDto getMyProfile(Member member) {
        // 혹시 요청한 멤버가 삭제된 멤버일 수 있으니 Repository 에 찾아서 DTO 를 만든다
        Member findMember = memberRepository.findById(member.getId())
                .orElseThrow(NotFoundException.NotFoundMemberException::new);

        return new ProfileDto(findMember);
    }

    @Override
    public BoardPageDto getMyBoards(Long memberId, Pageable pageable) {

        Page<Board> boardList = boardRepository.findAllByMemberId(memberId, pageable);

        List<BoardPageDto.BoardContent> boardContents = boardList.getContent().stream().map(BoardPageDto.BoardContent::new)
                .collect(Collectors.toList());

        return BoardPageDto.builder()
                .contents(boardContents)
                .numberOfElements(boardList.getNumberOfElements())
                .totalElements(boardList.getNumberOfElements())
                .totalPages(boardList.getTotalPages())
                .currentPage(pageable.getPageNumber())
                .build();
    }


    @Override
    public List<ProfileDto> getMyBuddies(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(NotFoundException.NotFoundMemberException::new);

        List<Member> buddies = findMember.getMyBuddies();
        return ProfileDto.of(buddies);
    }

    @Override
    public List<BuddyRequestDto> getBuddyRequests(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(NotFoundException.NotFoundMemberException::new);

        List<Member> notYetBuddyList = findMember.getNotYetBuddies();
        return BuddyRequestDto.of(notYetBuddyList);
    }

    @Override
    public ResponseEntity<String> changeMyProfile(Member member, UpdateProfileRequest request,
            MultipartFile image) throws IOException {
        Member findMember = memberRepository.findById(member.getId())
                .orElseThrow(NotFoundException.NotFoundMemberException::new);
        Profile findMemberProfile = findMember.getProfile();

        FileDetail fileDetail = fileUploadService.save(image);

        findMemberProfile.changeProfile(request, fileDetail.getPath());
        return new ResponseEntity<>("프로필이 변경되었습니다.", HttpStatus.OK);
    }

    @Override
    public ProfileDto getOtherProfile(Long userId) {
        Member findMember = memberRepository.findById(userId)
                .orElseThrow(NotFoundException.NotFoundMemberException::new);

        return new ProfileDto(findMember);
    }

    @Override
    public ResponseEntity<String> requestBuddy(Long memberId, Long targetUserId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(NotFoundException.NotFoundMemberException::new);
        Member targetMember = memberRepository.findById(targetUserId)
                .orElseThrow(NotFoundException.NotFoundMemberException::new);

        targetMember.addNotYetBuddies(findMember);
        return new ResponseEntity<>("친구 요청되었습니다.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> answerBuddyRequest(Long memberId, Long requestMemberId,
            Boolean answer) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(NotFoundException.NotFoundMemberException::new);
        findMember.changeNotYetBuddies(requestMemberId, answer);
        String message = answer ? "친구 등록되었습니다." : "요청이 거부되었습니다.";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changeMannerPoints(MannerPointsRequest request) {
        Member targetMember = memberRepository.findById(request.getTargetId())
                .orElseThrow(NotFoundException.NotFoundMemberException::new);

        targetMember.changeMannerPoints(request.getUpDown());
        return new ResponseEntity<>("평가가 완료되었습니다.", HttpStatus.OK);
    }

    public Member responseMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(NotFoundException.NotFoundMemberException::new);
    }

    @Override
    public ResponseEntity<String> deleteMyBuddy(Long memberId, Long buddyId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(NotFoundException.NotFoundMemberException::new);

        findMember.deleteBuddy(buddyId);
        return new ResponseEntity<>("친구가 삭제되었습니다.", HttpStatus.OK);
    }
}
