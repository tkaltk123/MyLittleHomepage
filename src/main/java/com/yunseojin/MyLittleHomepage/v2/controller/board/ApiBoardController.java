package com.yunseojin.MyLittleHomepage.v2.controller.board;

import com.yunseojin.MyLittleHomepage.v2.application.board.dto.query.GetAllBoardQuery;
import com.yunseojin.MyLittleHomepage.v2.application.board.dto.response.BoardResponse;
import com.yunseojin.MyLittleHomepage.v2.application.contract.service.ApplicationService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/boards")
@RestController
public class ApiBoardController {

    private final ApplicationService service;

    @GetMapping("")
    @ApiOperation(value = "게시판 목록 조회", notes = "모든 게시판의 목록을 조회합니다.")
    public ResponseEntity<List<BoardResponse>> getBoards() {

        return ResponseEntity.ok(service.executeQuery(new GetAllBoardQuery()));
    }
}
