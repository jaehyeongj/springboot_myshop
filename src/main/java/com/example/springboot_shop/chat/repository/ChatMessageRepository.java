package com.example.springboot_shop.chat.repository;

import com.example.springboot_shop.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByRoomId(Long roomId);
}
