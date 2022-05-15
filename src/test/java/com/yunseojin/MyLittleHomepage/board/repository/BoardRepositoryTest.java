package com.yunseojin.MyLittleHomepage.board.repository;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("저장")
    public void save() {
        //given
        var board = BoardEntity.builder()
                .name("게시판1")
                .build();

        //when
        var dbBoard = boardRepository.save(board);

        // then
        assertEquals(board, dbBoard);
        assertEquals(board.getName(), dbBoard.getName());
    }

    @Test
    @DisplayName("이름으로 찾기")
    public void findByName() {
        //given
        var board = BoardEntity.builder()
                .name("게시판1")
                .build();

        //when
        boardRepository.save(board);
        var dbBoard = boardRepository.findByName(board.getName());

        // then
        assertEquals(board, dbBoard);
        assertEquals(board.getName(), dbBoard.getName());
    }

    @Test
    @DisplayName("수정")
    public void update() {
        //given
        var board = BoardEntity.builder()
                .name("게시판1")
                .build();

        //when
        var dbBoard = boardRepository.save(board);
        board.setName("게시판2");

        // then
        assertEquals("게시판2", dbBoard.getName());
    }

    @Test
    @DisplayName("삭제")
    public void delete() {
        //given
        var board = BoardEntity.builder()
                .name("게시판1")
                .build();

        //when
        var dbBoard = boardRepository.save(board);
        boardRepository.delete(dbBoard);
        var dbBoard2 = boardRepository.findById(dbBoard.getId());
        var dbBoard3 = boardRepository.findByName(dbBoard.getName());

        // then
        assertEquals(Optional.empty(), dbBoard2);
        assertNull(dbBoard3);
    }
}