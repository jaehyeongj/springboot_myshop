package com.example.springboot_shop.chat.service;

import com.example.springboot_shop.chat.dto.ChatRoomDto;
import com.example.springboot_shop.chat.entity.ChatMessage;
import com.example.springboot_shop.member.dto.BuddyDto;
import com.example.springboot_shop.member.entity.Member;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ChatRoomService {


    ResponseEntity<List<BuddyDto>> getFriends(Member member);

    ResponseEntity<ChatRoomDto> getChatRoom(String friendNick, Member member);

    List<ChatMessage> getChatMessage(Long roomId);
}
