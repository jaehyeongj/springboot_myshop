package com.example.springboot_shop.redis;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public interface RedisService {


    void addRefreshTokenByRedis(String email, String refreshToken, Duration duration);
    void logoutAccessTokenByRedis(String accessToken, String logout, Long expiretime, TimeUnit milliseconds);
    void deleteRefreshTokenByRedis(String email);
}
