package com.example.springboot_shop.board.service;


import com.example.springboot_shop.board.dto.BoardResponse;
import com.example.springboot_shop.board.dto.CreateBoardRequest;
import com.example.springboot_shop.board.dto.UpdateBoardRequest;
import com.example.springboot_shop.board.entity.Board;
import com.example.springboot_shop.board.repository.BoardRepository;
import com.example.springboot_shop.comment.dto.CommentResponse;
import com.example.springboot_shop.comment.entity.Comment;
import com.example.springboot_shop.comment.repository.CommentRepository;
import com.example.springboot_shop.exception.NotFoundException;
import com.example.springboot_shop.like.repository.LikeRepository;
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
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    private final FileUploadService fileUploadService;

    //게시글 작성
    public void createBoard(CreateBoardRequest createBoardRequest, Member member, MultipartFile image) throws IOException {
        FileDetail fileDetail = fileUploadService.save(image);
        Board board = new Board(fileDetail.getPath(), createBoardRequest.getContent(), member);
        boardRepository.save(board);
    }

    //게시글 조회 -  페이지값을 입력할 때 게시글과 댓글페이지가 1 2 3 같이 이동?...
    public List<BoardResponse> getBoardList() {
        Page<Board> boardPage = boardRepository.findAll(pageableSetting(1));
        List<BoardResponse> boardResponseList = new ArrayList<>();
        for (Board board : boardPage) {
            Page<Comment> commentPage = commentRepository.findAllByBoardId(board.getId(), pageableSetting(1));
            List<CommentResponse> commentList = new ArrayList<>();
            for (Comment comment : commentPage) {
                commentList.add(new CommentResponse(comment));
            }
            Long likeCount = likeRepository.countByBoardId(board.getId());
            boardResponseList.add(new BoardResponse(board,likeCount));
        }
        return boardResponseList;
    }

    //게시글 수정
    public void updateBoard(Long boardId, UpdateBoardRequest boardRequest, Member member, MultipartFile image) throws IOException {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException());
        board.checkUser(board, member);
        FileDetail fileDetail = fileUploadService.save(image);
        board.updateBoard(boardRequest, fileDetail.getPath(), member);
        boardRepository.save(board);
    }

    //게시글 삭제
    public void deleteBoard(Long boardId, Member member) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException());
        board.checkUser(board, member);
        likeRepository.deleteAllByBoardId(boardId);
        boardRepository.deleteById(boardId);
    }

    //페이징
    public static Pageable pageableSetting(int page) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "modifiedAt");
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        return pageable;
    }

    //게시글 검색
    public List<BoardResponse> searchBoard(String searchName) {
        List<Board> boardList = boardRepository.searchBoard(searchName);
        List<BoardResponse> boardResponseList = new ArrayList<>();
        for (Board board : boardList) {
            Long likeCount = likeRepository.countByBoardId(board.getId());
            boardResponseList.add(new BoardResponse(board,likeCount));
        }
        return boardResponseList;
    }

    public BoardResponse getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new IllegalArgumentException(""));
        BoardResponse boardResponse = new BoardResponse(board);
        return boardResponse;
    }
}