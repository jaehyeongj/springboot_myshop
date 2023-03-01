package com.example.springboot_shop.security;//package com.nbcamp.gamematching.matchingservice.security;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.nbcamp.gamematching.matchingservice.auth.service.KakaoService;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.io.UnsupportedEncodingException;
//
//@Controller
//@RequiredArgsConstructor
//public class HomeController {
//
//    private final KakaoService kakaoService;
//
//    @RequestMapping("/")
//    public String home() {
//        return "auth/auth";
//    }
//
//    @RequestMapping("/main")
//    public String main() {
//        return "board/join";
//    }
//
//    @RequestMapping("/google")
//    public String kakao() {
//        return "board/test";
//    }
//    @RequestMapping("/board")
//    public String boardd() {
//        return "board/board";
//    }
//
////    @GetMapping("/kakao/login")
////    @ResponseBody
////    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {
////
////        String createToken = kakaoService.kakaoLogin(code, response);
////
////        return createToken;
////
////
////    }
//
//    @GetMapping("/api/auth/kakao/callback")
//    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {
//        // code: 카카오 서버로부터 받은 인가 코드
//
//        String token = kakaoService.kakaoLogin(code, response);
//
//        response.setHeader("Authorization", token);
//
////        포트번호 5500
//        return "redirect:http://localhost:8080/google?token="+ token;
//    }
//}
