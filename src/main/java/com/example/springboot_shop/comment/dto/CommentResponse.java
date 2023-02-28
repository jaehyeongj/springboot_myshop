package com.example.springboot_shop.comment.dto;


import com.example.springboot_shop.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private final Long id;
    private final String nickname;
    private final String memberImage;
    private final String content;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.nickname = comment.getNickname();
        this.memberImage = comment.getBoard().getMember().getProfile().getProfileImage();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }


}


