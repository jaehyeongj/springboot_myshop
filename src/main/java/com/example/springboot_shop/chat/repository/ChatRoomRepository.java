package com.example.springboot_shop.chat.repository;

import com.example.springboot_shop.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    boolean existsByRoomName(String roomName);

    ChatRoom findByRoomName(String roomName);
}
