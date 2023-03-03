package com.example.springboot_shop.board.repository.query;


import com.example.springboot_shop.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardQueryRepository {

    Page<Board> findAllBoard(Pageable pageable);

    Page<Board> findMyBoard(Long memberId, Pageable pageable); //프로필 내가 쓴 글 조회

    List<Board> searchBoard(String searchName);

}
