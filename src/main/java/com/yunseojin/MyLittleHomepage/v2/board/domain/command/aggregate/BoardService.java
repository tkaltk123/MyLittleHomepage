package com.yunseojin.MyLittleHomepage.v2.board.domain.command.aggregate;

import com.yunseojin.MyLittleHomepage.v2.board.domain.query.entity.QueriedBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@RequiredArgsConstructor
public class BoardService {

    public Board create(QueriedBoard boardInfo) {
        return new Board(boardInfo);
    }

    public Board update(Board board, QueriedBoard boardInfo) {
        return board.update(boardInfo);
    }

    public void delete(Board board) {
        board.delete();
    }
}
