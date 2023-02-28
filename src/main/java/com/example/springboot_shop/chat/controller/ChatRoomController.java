package com.example.springboot_shop.chat.controller;


import com.example.springboot_shop.chat.dto.ChatRoomDto;
import com.example.springboot_shop.chat.entity.ChatMessage;
import com.example.springboot_shop.chat.service.ChatRoomService;
import com.example.springboot_shop.member.dto.BuddyDto;
import com.example.springboot_shop.member.entity.Member;
import com.example.springboot_shop.member.repository.MemberRepository;
import com.example.springboot_shop.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final MemberRepository memberRepository;


    //채팅 페이지 들어가기 => 수정 필요!
    @GetMapping("/room")
    public String rooms(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "/chat/chat";
    }

    //테스트용
    @GetMapping("/member")
    @ResponseBody
    public List<Member> members() {
        return memberRepository.findAll();
    }


    //친구 목록 불러오기
    @GetMapping("/friends")
    @ResponseBody
    public ResponseEntity<List<BuddyDto>> getFriends(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatRoomService.getFriends(userDetails.getMember());
    }


    //하나의 채팅방 정보 가져오기 - 채팅방 리스트에서 클릭하면 id 가져와서
    @GetMapping("/room/enter/{friendNick}")
    @ResponseBody
    public ResponseEntity<ChatRoomDto> getChatRoom(@PathVariable String friendNick, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatRoomService.getChatRoom(friendNick, userDetails.getMember());
    }

    //이전의 채팅 기록 가져오기
    @GetMapping("/room/{roomId}/message")
    @ResponseBody
    public ResponseEntity<List<ChatMessage>> getChatMessage(@PathVariable Long roomId) {
        List<ChatMessage> chatMessageList = chatRoomService.getChatMessage(roomId);
        return ResponseEntity.ok().body(chatMessageList);
    }


}