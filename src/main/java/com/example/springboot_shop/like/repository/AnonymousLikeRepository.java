package com.example.springboot_shop.like.repository;


import com.example.springboot_shop.board.entity.AnonymousBoard;
import com.example.springboot_shop.like.entity.AnonymousLike;
import com.example.springboot_shop.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonymousLikeRepository extends JpaRepository<AnonymousLike,Long> {

    Long countByAnonymousBoardId(Long boardId);


    void deleteAllByAnonymousBoardId(Long boardId);

    boolean existsByMemberAndAnonymousBoard(Member member, AnonymousBoard board);

    void deleteByMemberAndAnonymousBoard(Member member, AnonymousBoard board);
}
