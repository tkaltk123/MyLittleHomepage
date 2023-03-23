package com.yunseojin.MyLittleHomepage.domain.board.command.model;

import com.yunseojin.MyLittleHomepage.domain.board.command.validation.BoardValidator;
import com.yunseojin.MyLittleHomepage.domain.board.query.model.QueriedBoard;
import com.yunseojin.MyLittleHomepage.domain.contract.command.validation.Create;
import com.yunseojin.MyLittleHomepage.domain.contract.command.validation.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardValidator validator;

    @Validated(Create.class)
    public Board create(QueriedBoard boardInfo) {

        validator.validateName(boardInfo, null);
        return new Board(boardInfo);
    }

    @Validated(Update.class)
    public Board update(Board board, QueriedBoard boardInfo) {

        validator.validateName(boardInfo, board.getName());
        return board.update(boardInfo);
    }

    public void delete(Board board) {
        board.delete();
    }
}
