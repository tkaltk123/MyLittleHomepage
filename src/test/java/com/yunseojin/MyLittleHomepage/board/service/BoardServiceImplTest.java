package com.yunseojin.MyLittleHomepage.board.service;

import com.yunseojin.MyLittleHomepage.TestUtil;
import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.board.repository.BoardRepository;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.member.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class BoardServiceImplTest {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @BeforeEach
    void init() {

        var board1 = BoardEntity.builder()
                .name("testBoard")
                .build();

        var board2 = BoardEntity.builder().
                name("testBoard2")
                .build();

        boardRepository.save(board1);
        boardRepository.save(board2);
    }

    @Test
    void getBoard() {

        var boardRes = boardService.getBoard("testBoard");

        TestUtil.assertError(ErrorMessage.NOT_EXISTS_BOARD_EXCEPTION, () ->
                boardService.getBoard("board"));

        assertEquals(boardRes.getName(), "testBoard");
    }

    @Test
    void getBoardList() {

        var boardResList = boardService.getBoardList();

        assertTrue(boardResList.stream().anyMatch(d -> d.getName().equals("testBoard")));
        assertTrue(boardResList.stream().anyMatch(d -> d.getName().equals("testBoard2")));
    }
}