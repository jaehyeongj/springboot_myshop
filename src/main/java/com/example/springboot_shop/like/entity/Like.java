package com.example.springboot_shop.like.entity;

import com.example.springboot_shop.board.entity.Board;
import com.example.springboot_shop.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "Likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public Like(Board board, Member member) {
        this.member = member;
        this.board = board;
    }

    public void checkUser(Like like, Member member) {
        if (!like.getMember().getEmail().equals(member.getEmail())) throw new IllegalArgumentException("유저 불일치");
    }

    
}
