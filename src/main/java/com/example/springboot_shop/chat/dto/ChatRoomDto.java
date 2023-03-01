package com.example.springboot_shop.chat.dto;


import com.example.springboot_shop.chat.entity.ChatRoom;
import lombok.Getter;

@Getter
public class ChatRoomDto {
    private Long id;
    private String roomName;
    private String nickNameA;
    private String nickNameB;

    public ChatRoomDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.roomName = chatRoom.getRoomName();
        this.nickNameA = chatRoom.getUser1().getProfile().getNickname();
        this.nickNameB = chatRoom.getUser2().getProfile().getNickname();
    }
}
