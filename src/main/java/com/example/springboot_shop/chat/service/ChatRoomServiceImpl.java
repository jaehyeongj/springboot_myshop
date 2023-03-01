package com.example.springboot_shop.chat.service;


import com.example.springboot_shop.chat.dto.ChatRoomDto;
import com.example.springboot_shop.chat.entity.ChatMessage;
import com.example.springboot_shop.chat.entity.ChatRoom;
import com.example.springboot_shop.chat.repository.ChatMessageRepository;
import com.example.springboot_shop.chat.repository.ChatRoomRepository;
import com.example.springboot_shop.exception.NotFoundException;
import com.example.springboot_shop.member.dto.BuddyDto;
import com.example.springboot_shop.member.entity.Member;
import com.example.springboot_shop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.springboot_shop.chat.entity.ChatRoom.createRoomName;


@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatMessageRepository chatMessageRepository;


    //내 친구 리스트 가져오기
    @Override
    public ResponseEntity<List<BuddyDto>> getFriends(Member member) {
        List<Member> friends = member.getMyBuddies();
        return ResponseEntity.ok().body(BuddyDto.of(friends));
    }


    //채팅방 생성하기
    @Transactional
    public ChatRoomDto createChatRoom(Member member1, Member member2) {
        ChatRoom chatRoom = ChatRoom.create(member1, member2);
        chatRoomRepository.save(chatRoom);
        return new ChatRoomDto(chatRoom);
    }


    //선택한 채팅방 가져오기
    @Override
    public ResponseEntity<ChatRoomDto> getChatRoom(String friendNick, Member me) {

        Member friend = memberRepository.findByProfileNickname(friendNick)
                .orElseThrow(NotFoundException.NotFoundMemberException::new);

        String roomName = createRoomName(friendNick, me.getProfile().getNickname());

        //채팅방이 있으면 채팅방 가져오기
        if (chatRoomRepository.existsByRoomName(roomName)) {
            return ResponseEntity.ok().body(new ChatRoomDto(chatRoomRepository.findByRoomName(roomName)));
        }
        //채팅방이 없으면 채팅방 생성
        else {
            return ResponseEntity.ok().body(createChatRoom(friend, me));
        }
    }

    @Override
    public List<ChatMessage> getChatMessage(Long roomId) {
        return chatMessageRepository.findAllByRoomId(roomId);
    }


}
