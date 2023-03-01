package com.example.springboot_shop.board.entity;


import com.example.springboot_shop.board.dto.UpdateBoardRequest;
import com.example.springboot_shop.comment.entity.Comment;
import com.example.springboot_shop.common.entity.BaseEntity;
import com.example.springboot_shop.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@Getter
@NoArgsConstructor
@Entity
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="board_id")
    private Long id;

    private String nickname;

    private String boardImage;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @OneToMany(mappedBy = "board",cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    public Board(String boardImage, String content, Member member) {
        this.nickname = member.getProfile().getNickname();
        this.boardImage = boardImage;
        this.content = content;
        this.member = member;
    }

    public void updateBoard(UpdateBoardRequest boardRequestDto, String boardImageUrl, Member member) throws IOException {
        this.boardImage = boardImageUrl;
        this.content = boardRequestDto.getContent();
        this.member = member;
    }

    public void checkUser(Board board, Member member) {
        if (!board.getMember().getEmail().equals(member.getEmail())) throw new IllegalArgumentException("유저가 일치하지 않습니다.");
    }
}
