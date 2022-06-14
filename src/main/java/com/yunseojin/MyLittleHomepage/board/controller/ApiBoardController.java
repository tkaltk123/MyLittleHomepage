package com.yunseojin.MyLittleHomepage.board.controller;

import com.yunseojin.MyLittleHomepage.board.service.BoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/boards")
@RestController
public class ApiBoardController {
    private final BoardService boardService;

    @GetMapping("")
    @ApiOperation(value = "게시판 목록 조회", notes = "모든 게시판의 목록을 조회합니다.")
    public ResponseEntity<?> getBoards() {
        return new ResponseEntity<>(boardService.getBoardList(), HttpStatus.OK);
    }
}
