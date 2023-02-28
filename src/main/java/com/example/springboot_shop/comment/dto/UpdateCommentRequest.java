package com.example.springboot_shop.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCommentRequest {

    private String content;

    public UpdateCommentRequest(String content) {
        this.content = content;
    }
}
