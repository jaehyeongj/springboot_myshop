package com.example.springboot_shop.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateBoardRequest {

    private String content;

    public UpdateBoardRequest(String content) {
        this.content = content;
    }
}
