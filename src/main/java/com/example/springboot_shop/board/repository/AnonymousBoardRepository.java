package com.example.springboot_shop.board.repository;

import com.example.springboot_shop.board.entity.AnonymousBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonymousBoardRepository extends JpaRepository<AnonymousBoard,Long> {
}
