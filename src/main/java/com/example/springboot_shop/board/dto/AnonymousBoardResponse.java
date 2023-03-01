package com.example.springboot_shop.board.dto;


import com.example.springboot_shop.board.entity.AnonymousBoard;
import com.example.springboot_shop.comment.dto.AnonymousCommentResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AnonymousBoardResponse {


    private final Long id;
    private final String nickname;
    private final String boardImage;
    private final String content;
    private List<AnonymousCommentResponse> comments;
    private Long likeCount;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;


    public AnonymousBoardResponse(AnonymousBoard board, List<AnonymousCommentResponse> comments, Long likeCount) {
        this.id = board.getId();
        this.nickname = board.getNickname();
        this.boardImage = board.getBoardImage();
        this.content = board.getContent();
        this.comments = comments;
        this.likeCount = likeCount;
        this.createAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

    public AnonymousBoardResponse(AnonymousBoard board) {
        this.id = board.getId();
        this.nickname = board.getNickname();
        this.boardImage = board.getBoardImage();
        this.content = board.getContent();
        this.createAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}

