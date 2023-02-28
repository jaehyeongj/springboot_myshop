package com.example.springboot_shop.like.repository;


import com.example.springboot_shop.board.entity.Board;
import com.example.springboot_shop.like.entity.Like;
import com.example.springboot_shop.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {

    Long countByBoardId(Long boardId);

    void deleteAllByBoardId(Long boardId);

    void deleteByMemberAndBoard(Member member, Board board);

    boolean existsByMemberAndBoard(Member member, Board board);
}
