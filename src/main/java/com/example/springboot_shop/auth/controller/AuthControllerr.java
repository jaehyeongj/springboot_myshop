package com.example.springboot_shop.auth.controller;//package com.nbcamp.gamematching.matchingservice.auth.controller;
//
//import com.nbcamp.gamematching.matchingservice.auth.dto.SigninRequest;
//import com.nbcamp.gamematching.matchingservice.auth.dto.SignupRequest;
//import com.nbcamp.gamematching.matchingservice.auth.service.AuthService;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.UnsupportedEncodingException;
//
//@Controller
//@RequiredArgsConstructor
//
//public class AuthController {
//
//    private final AuthService authService;
//
//
//
//    @PostMapping("/signup")
//    @ResponseBody
//    public void signup(@RequestBody SignupRequest signupRequest) {
//        authService.signUp(signupRequest);
//    }
//
//    @PostMapping("/signin")
//    @ResponseBody
//    public void signIn(@RequestBody SigninRequest signinRequest, HttpServletResponse response) throws UnsupportedEncodingException {
//        authService.signIn(signinRequest, response);
//    }
//
//
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }
//
//    @GetMapping("/join")
//    public String join() {
//        return "join";
//    }
//
//
//
//    @GetMapping({"", "/"})
//    public String board() {
//        return "board/board";
//    }
//
//    @GetMapping("/main/anonymous")
//    public String anonymousBoard() {
//        return "board/anonymous-board";
//    }
//
//    @GetMapping("/main")
//    public String main() {
//        return "auth/auth";
//    }
//
//    @GetMapping("/update/{boardId}")
//    public String update(@PathVariable Long boardId) {
//        return "board/board-update";
//    }
//
//    @GetMapping("/anonymous/update/{boardId}")
//    public String anonymousUpdate(@PathVariable Long boardId) {
//        return "board/anonymous-board-update";
//    }
//
//    @GetMapping("/comment/update/{commentId}")
//    public String commentUpdate(@PathVariable Long commentId) {
//        return "board/comment-update";
//    }
//
//    @GetMapping("/anonymous/comment/update/{commentId}")
//    public String anonymousCommentUpdate(@PathVariable Long commentId) {
//        return "board/anonymous-comment-update";
//    }
//
//
//
//}