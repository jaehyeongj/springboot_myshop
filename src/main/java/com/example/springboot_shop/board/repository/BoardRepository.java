package com.example.springboot_shop.board.repository;


import com.example.springboot_shop.board.entity.Board;
import com.example.springboot_shop.board.repository.query.BoardQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardQueryRepository {

    Page<Board> findAllByMemberId(Long memberId, Pageable pageable);
    
}
