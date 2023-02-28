package com.example.springboot_shop.member.entity;


import com.example.springboot_shop.chat.entity.ChatRoom;
import com.example.springboot_shop.exception.SignException;
import com.example.springboot_shop.member.domain.MemberRoleEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.util.regex.Pattern.matches;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    /**
     * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
     */
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    public String password;

    @Embedded
    private Profile profile;

    @Column
    private String email;

    @Column
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MemberRoleEnum role;

    private String provider;

    private String providerId;

    /**
     * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
     */
    @Builder
    public Member(String email, String password, Profile profile, MemberRoleEnum role, String provider, String providerId) {
        if (matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", email)) {
            this.email = email;
        } else {
            throw new SignException.InvalidEmail();
        }
        this.password = password;
        this.profile = profile;
        if (MemberRoleEnum.isContains(role)) {
            this.role = role;
        }
        this.provider = provider;
        this.providerId = providerId;
    }

    /**
     * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
     */
    @OneToMany
    private List<Member> myBuddies = new ArrayList<>();

    @OneToMany
    private List<Member> notYetBuddies = new ArrayList<>();

//    @OneToMany
//    private List<Board> boards = new ArrayList<>();


    /**
     * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
     */
//    public void addBoards(Board board) {
//        this.getBoards().add(board);
//    }
    public void addBuddies(Member member) {
        this.getMyBuddies().add(member);
    }

    public void addNotYetBuddies(Member member) {
        this.getNotYetBuddies().add(member);
    }

    /**
     * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
     */
    public void changeNotYetBuddies(Long requestMemberId, Boolean answer) {
        if (answer) {
            this.getNotYetBuddies().stream().forEach(
                    (member -> {
                        if (member.getId() == requestMemberId) {
                            addBuddies(member);
                        }
                    })
            );
        }
        this.getNotYetBuddies().removeIf(notYetBuddy -> (notYetBuddy.getId() == requestMemberId));
    }

    public void changeMannerPoints(String upDown) {
        this.getProfile().changeMannerPoints(upDown);
    }

    public void deleteBuddy(Long memberId) {
        this.getMyBuddies().removeIf(member -> (member.getId() == memberId));
    }
}
