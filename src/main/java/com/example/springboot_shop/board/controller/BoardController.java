package com.example.springboot_shop.board.controller;


import com.example.springboot_shop.board.dto.AnonymousBoardResponse;
import com.example.springboot_shop.board.dto.BoardResponse;
import com.example.springboot_shop.board.dto.CreateBoardRequest;
import com.example.springboot_shop.board.dto.UpdateBoardRequest;
import com.example.springboot_shop.board.service.AnonymousBoardService;
import com.example.springboot_shop.board.service.BoardService;
import com.example.springboot_shop.member.service.FileUploadService;
import com.example.springboot_shop.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final AnonymousBoardService anonymousBoardService;

    @Value("${file.dir}")
    private String fileDir;

    private final FileUploadService fileUploadService;



    //게시글 작성
    @PostMapping(value = "/normal")
        public ResponseEntity<String> createBoard(@RequestPart("requestDto") CreateBoardRequest createBoardRequest, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestPart(value ="image", required = false) MultipartFile image) throws IOException {
        boardService.createBoard(createBoardRequest, userDetails.getUser(),image);
        return new ResponseEntity<>("게시글 작성 완료", HttpStatus.CREATED);
//        return "board/create";
    }

    //익명 게시글 작성
    @PostMapping(value = "/anonymous")
    public ResponseEntity<String> createAnonymousBoard(@RequestPart("requestDto") CreateBoardRequest createBoardRequest, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestPart("image") MultipartFile image) throws IOException {
        anonymousBoardService.createAnonymousBoard(createBoardRequest, userDetails.getUser(),image);
        return new ResponseEntity<>("게시글 작성 완료", HttpStatus.CREATED);
//        return "templates/anonymous/create";
    }

    //게시글 조회
    @GetMapping("/normal")
    public List<BoardResponse> getBoardList() {
       return boardService.getBoardList();
//        model.addAttribute("boardResponseList",boardResponseList);
//        model.addAttribute("board",board);
//        return "board/main";
    }

    //익명 게시글 조회
    @GetMapping(value = "/anonymous")
    public  List<AnonymousBoardResponse> getAnonymousBoardList() {
        return anonymousBoardService.getAnonymousBoardList();
//        model.addAttribute("getAnonymousList",anonymousBoardResponseList);
//        model.addAttribute("board",board);
//        return "anonymous/main";
    }

    //게시글 수정
    @PutMapping(value = "/normal/{boardId}") //수정할 때 이미지나 내용중 하나만 변경하고 싶을 때는?
    public ResponseEntity<String> updateBoard(@PathVariable("boardId") Long boardId, @RequestPart("requestDto") UpdateBoardRequest updateBoardRequest, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestPart(required = false,name ="image") MultipartFile image) throws IOException {
        boardService.updateBoard(boardId,updateBoardRequest,userDetails.getMember(),image);
        return new ResponseEntity<>("게시글 수정완료",HttpStatus.OK);
        //        return "board/update";
    }

    //익명 게시글 수정
    @PutMapping(value = "/anonymous/{boardId}")
    public ResponseEntity<String> updateAnonymousBoard(@PathVariable("boardId") Long boardId, @RequestPart("requestDto") UpdateBoardRequest updateBoardRequest, @AuthenticationPrincipal UserDetailsImpl userDetails,@RequestPart("image") MultipartFile image) throws IOException {
        anonymousBoardService.updateAnonymousBoard(boardId,updateBoardRequest,userDetails.getMember(),image);
        return new ResponseEntity<>("게시글 수정완료",HttpStatus.OK);
//        return "anonymous/update";
    }

    //게시글 삭제
    @DeleteMapping(value = "/normal/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable("boardId") Long boardId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.deleteBoard(boardId,userDetails.getMember());
        return new ResponseEntity<>("게시글 삭제완료",HttpStatus.OK);
//        return "board/delete";
    }

    //익명 게시글 삭제
    @DeleteMapping(value = "/anonymous/{boardId}")
    public ResponseEntity<String> deleteAnonymousBoard(@PathVariable("boardId") Long boardId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        anonymousBoardService.deleteAnonymousBoard(boardId,userDetails.getMember());
        return new ResponseEntity<>("게시글 삭제완료",HttpStatus.OK);
//        return "anonymous/delete";
    }


    //게시글 단건 조회
    @GetMapping("/normal/{boardId}")
    public BoardResponse getBoard(@PathVariable Long boardId) {
        return boardService.getBoard(boardId);
    }

    //익명 게시글 단건 조회
    @GetMapping("/anonymous/{boardId}")
    public AnonymousBoardResponse getAnonymousBoard(@PathVariable Long boardId) {
        return anonymousBoardService.getAnonymousBoard(boardId);
    }





//    @GetMapping(value = "/search/{searchName}")
//    public List<BoardResponse> getBoardList1(@PathVariable String searchName) {
//        return boardService.getBoardList1(searchName);
////        model.addAttribute("boardResponseList",boardResponseList);
////        model.addAttribute("board",board);
////        return "board/main";
//    }

}
