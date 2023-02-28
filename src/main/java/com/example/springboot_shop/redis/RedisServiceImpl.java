package com.example.springboot_shop.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();



    public void addRefreshTokenByRedis(String email, String refreshToken, Duration duration) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        stringValueOperations.set(email, refreshToken, duration);
        log.info("Redis email : " + email);
        log.info("Redis refreshToken : " + stringValueOperations.get(refreshToken));
    }

    public void logoutAccessTokenByRedis(String accessToken, String logout, Long expiretime, TimeUnit milliseconds) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        stringValueOperations.set(accessToken, logout, expiretime,milliseconds);
        log.info("Redis logout : " + accessToken);
    }
    public void deleteRefreshTokenByRedis(String email){
        stringRedisTemplate.delete(email);
    }


}
