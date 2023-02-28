package com.example.springboot_shop.board.dto;


import com.example.springboot_shop.board.entity.Board;
import com.example.springboot_shop.comment.dto.CommentResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardResponse {

    private final Long id;
    private final String nickname;
    private final String memberImage;
    private final String boardImage;
    private final String content;
    private List<CommentResponse> comments;
    private Long likeCount;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;


    public BoardResponse(Board board, List<CommentResponse> comments, Long likeCount) {
        this.id = board.getId();
        this.nickname = board.getMember().getProfile().getNickname();
        this.memberImage = board.getMember().getProfile().getProfileImage();
        this.boardImage = board.getBoardImage();
        this.content = board.getContent();
        this.comments = comments;
        this.likeCount = likeCount;
        this.createAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.nickname = board.getMember().getProfile().getNickname();
        this.memberImage = board.getMember().getProfile().getProfileImage();
        this.boardImage = board.getBoardImage();
        this.content = board.getContent();
        this.createAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }


}
