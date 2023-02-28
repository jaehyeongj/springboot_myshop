package com.example.springboot_shop.member.repository;


import com.example.springboot_shop.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<Member> findByProfileNickname(String friendNick);

    Member findByEmailAndProvider(String email, String provider);
}
