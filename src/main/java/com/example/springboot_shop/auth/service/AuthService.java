package com.example.springboot_shop.auth.service;


import com.example.springboot_shop.auth.dto.SigninRequest;
import com.example.springboot_shop.auth.dto.SignupRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;

public interface AuthService {
    void signUp(SignupRequest signupRequest);

    void signIn(SigninRequest signinRequest, HttpServletResponse response) throws UnsupportedEncodingException;
    void signOut(HttpServletRequest request);
}
