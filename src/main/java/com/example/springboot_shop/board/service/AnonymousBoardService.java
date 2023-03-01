package com.example.springboot_shop.board.service;


import com.example.springboot_shop.board.dto.AnonymousBoardResponse;
import com.example.springboot_shop.board.dto.CreateBoardRequest;
import com.example.springboot_shop.board.dto.UpdateBoardRequest;
import com.example.springboot_shop.board.entity.AnonymousBoard;
import com.example.springboot_shop.board.repository.AnonymousBoardRepository;
import com.example.springboot_shop.comment.dto.AnonymousCommentResponse;
import com.example.springboot_shop.comment.entity.AnonymousComment;
import com.example.springboot_shop.comment.repository.AnonymousCommentRepository;
import com.example.springboot_shop.like.repository.AnonymousLikeRepository;
import com.example.springboot_shop.member.domain.FileDetail;
import com.example.springboot_shop.member.entity.Member;
import com.example.springboot_shop.member.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AnonymousBoardService {

    private final AnonymousBoardRepository anonymousBoardRepository;
    private final AnonymousCommentRepository anonymousCommentRepository;
    private final AnonymousLikeRepository anonymousLikeRepository;

    private final FileUploadService fileUploadService;


    //익명 게시글 작성
    public void createAnonymousBoard(CreateBoardRequest createBoardRequest, Member member, MultipartFile image) throws IOException {
        FileDetail fileDetail = fileUploadService.save(image);
        AnonymousBoard board = new AnonymousBoard(AnonymousBoard.nNick(), fileDetail.getPath(), createBoardRequest.getContent(),member);
        anonymousBoardRepository.save(board);
    }

    //익명 게시글 조회
    public List<AnonymousBoardResponse> getAnonymousBoardList() {
        Page<AnonymousBoard> boardPage = anonymousBoardRepository.findAll(pageableSetting(1));
        List<AnonymousBoardResponse> boardResponseList = new ArrayList<>();
        for (AnonymousBoard board : boardPage) {
            Page<AnonymousComment> commentPage = anonymousCommentRepository.findAllByAnonymousBoardId(board.getId(), pageableSetting(1));
            List<AnonymousCommentResponse> commentList = new ArrayList<>();
            for (AnonymousComment comment : commentPage) {
                commentList.add(new AnonymousCommentResponse(comment));
            }
            Long likeCount = anonymousLikeRepository.countByAnonymousBoardId(board.getId());
            boardResponseList.add(new AnonymousBoardResponse(board, commentList, likeCount));
        }
        return boardResponseList;
    }

    //익명 게시글 수정
    public void updateAnonymousBoard(Long boardId, UpdateBoardRequest boardRequest, Member member, MultipartFile image) throws IOException {
        AnonymousBoard board = anonymousBoardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException());
        board.checkUser(board,member);
        FileDetail fileDetail = fileUploadService.save(image);
        board.updateAnonymousBoard(boardRequest,fileDetail.getPath(),member);
        anonymousBoardRepository.save(board);
    }

    //익명 게시글 삭제
    public void deleteAnonymousBoard(Long boardId,Member member) {
        AnonymousBoard board = anonymousBoardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException(""));
        board.checkUser(board,member);
        anonymousLikeRepository.deleteAllByAnonymousBoardId(boardId);
        anonymousBoardRepository.deleteById(boardId);
    }

    //페이징
    public Pageable pageableSetting(int page) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "modifiedAt");
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        return pageable;
    }

    public AnonymousBoardResponse getAnonymousBoard(Long boardId) {
        AnonymousBoard board = anonymousBoardRepository.findById(boardId).orElseThrow(()-> new IllegalArgumentException(""));
        AnonymousBoardResponse anonymousBoardResponse = new AnonymousBoardResponse(board);
        return anonymousBoardResponse;
    }
}
