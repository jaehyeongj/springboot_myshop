package com.example.springboot_shop.auth.service;

import com.example.springboot_shop.auth.dto.SigninRequest;
import com.example.springboot_shop.auth.dto.SignupRequest;
import com.example.springboot_shop.exception.ExistsException;
import com.example.springboot_shop.exception.SignException;
import com.example.springboot_shop.jwt.JwtUtil;
import com.example.springboot_shop.member.domain.MemberRoleEnum;
import com.example.springboot_shop.member.entity.Member;
import com.example.springboot_shop.member.entity.Profile;
import com.example.springboot_shop.member.repository.MemberRepository;
import com.example.springboot_shop.redis.RedisService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private static final long REFRESH_TOKEN_TIME = 24 * 60 * 60 * 1000L;

    private final RedisService redisService;

    @Override
    @Transactional
    public void signUp(SignupRequest signupRequest) {
        String email = signupRequest.getEmail();
        String password = signupRequest.getPassword();
        if (memberRepository.existsByEmail(email)) {
            throw new ExistsException.DuplicatedEmail();
        }
        if (!password.matches("\\w{8,16}")) {
            throw new SignException.InvalidPassword();
        }
        String encodedPassword = passwordEncoder.encode(password);
        Member member = Member.builder()
                .email(email)
                .password(encodedPassword)
                .profile(Profile.builder()
                        .nickname(signupRequest.getNickname())
                        .build())
                .role(MemberRoleEnum.USER)
                .build();
        log.info("가입된 멤버 : " + email);
        memberRepository.save(member);

    }

    @Override
    @Transactional(readOnly = true)
    public void signIn(SigninRequest signinRequest, HttpServletResponse response)
            throws UnsupportedEncodingException {
        String email = signinRequest.getEmail();
        String password = signinRequest.getPassword();
        Member member = memberRepository.findByEmail(email).orElseThrow(SignException::new);
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new SignException();
        }
        Cookie cookie = jwtUtil.createCookie(email);
        String refreshToken = cookie.getValue();
        String accessToken = jwtUtil.createAccessToken(member.getEmail(), member.getRole());
        response.addCookie(cookie);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
        redisService.addRefreshTokenByRedis(email, refreshToken,
                Duration.ofMillis(REFRESH_TOKEN_TIME));
        log.info("로그인 멤버 : " + email);
    }

    @Override
    @Transactional(readOnly = true)
    public void signOut(HttpServletRequest request) {
        String accessToken = jwtUtil.resolveToken(request);
        Claims atclaims = jwtUtil.getUserInfoFromToken(accessToken);
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshtoken")) {
                redisService.deleteRefreshTokenByRedis(atclaims.getSubject());
                Long exp = atclaims.getExpiration().getTime();
                redisService.logoutAccessTokenByRedis(accessToken, "logout",
                        exp - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                log.info("로그아웃 멤버 : " + atclaims.getSubject());
            } else {
                return;
            }
        }
    }

}
