package com.example.springboot_shop.comment.service;


import com.example.springboot_shop.board.entity.AnonymousBoard;
import com.example.springboot_shop.board.entity.Board;
import com.example.springboot_shop.board.repository.AnonymousBoardRepository;
import com.example.springboot_shop.board.repository.BoardRepository;
import com.example.springboot_shop.comment.dto.AnonymousCommentResponse;
import com.example.springboot_shop.comment.dto.CommentResponse;
import com.example.springboot_shop.comment.dto.UpdateCommentRequest;
import com.example.springboot_shop.comment.entity.AnonymousComment;
import com.example.springboot_shop.comment.entity.Comment;
import com.example.springboot_shop.comment.repository.AnonymousCommentRepository;
import com.example.springboot_shop.comment.repository.CommentRepository;
import com.example.springboot_shop.exception.NotFoundException;
import com.example.springboot_shop.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final AnonymousBoardRepository anonymousBoardRepository;
    private final CommentRepository commentRepository;
    private final AnonymousCommentRepository anonymousCommentRepository;

    //댓글 작성
    @Transactional
    public void createComment(Long boardId, String content, Member member) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException());
        Comment comment = new Comment(content, board,member);
        commentRepository.save(comment);
    }

    //익명 댓글 작성
    @Transactional
    public void createAnonymousComment(Long boardId, String content,Member member) {
        AnonymousBoard board = anonymousBoardRepository.findById(boardId).orElseThrow(() -> new NotFoundException());
        AnonymousComment comment = new AnonymousComment(content, board,member);
        anonymousCommentRepository.save(comment);
    }

    //댓글 수정
    @Transactional
    public void updateComment(Long commentId, UpdateCommentRequest updateCommentRequest, Member member) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException());
        comment.checkUser(comment,member);
        comment.updateComment(updateCommentRequest,member);
        commentRepository.save(comment);
    }

    //익명 댓글 수정
    @Transactional
    public void updateAnonymousComment(Long commentId,UpdateCommentRequest updateCommentRequest,Member member) {
        AnonymousComment comment = anonymousCommentRepository.findById(commentId).orElseThrow(() -> new NotFoundException());
        comment.checkUser(comment,member);
        comment.updateComment(updateCommentRequest,member);
        anonymousCommentRepository.save(comment);
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, Member member) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException());
        comment.checkUser(comment,member);
        commentRepository.deleteById(commentId);
    }

    //익명 댓글 삭제
    @Transactional
    public void deleteAnonymousComment(Long commentId,Member member) {
        AnonymousComment comment = anonymousCommentRepository.findById(commentId).orElseThrow(() -> new NotFoundException());
        comment.checkUser(comment,member);
        anonymousCommentRepository.deleteById(commentId);
    }

    public List<CommentResponse> showComment(Long boardId) {
        List<Comment> commentList = commentRepository.findAllByBoardId(boardId);
        List<CommentResponse> commentResponseList = new ArrayList<>();
        for(Comment comment: commentList) {
            CommentResponse commentResponse = new CommentResponse(comment);
            commentResponseList.add(commentResponse);
        }


        return commentResponseList;

    }

    public List<AnonymousCommentResponse> showAnonymousComment(Long boardId) {
        List<AnonymousComment> commentList = anonymousCommentRepository.findAllByAnonymousBoardId(boardId);
        List<AnonymousCommentResponse> commentResponseList = new ArrayList<>();
        for(AnonymousComment comment: commentList) {
            AnonymousCommentResponse commentResponse = new AnonymousCommentResponse(comment);
            commentResponseList.add(commentResponse);
        }


        return commentResponseList;

    }

    public CommentResponse getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException(""));
        CommentResponse commentResponse = new CommentResponse(comment);
        return commentResponse;
    }

    public AnonymousCommentResponse getAnonymousComment(Long commentId) {
        AnonymousComment comment = anonymousCommentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException(""));
        AnonymousCommentResponse commentResponse = new AnonymousCommentResponse(comment);
        return commentResponse;
    }
}
