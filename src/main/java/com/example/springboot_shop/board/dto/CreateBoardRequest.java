package com.example.springboot_shop.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateBoardRequest {

    private String content;

    public CreateBoardRequest(String content) {
        this.content = content;
    }
}
