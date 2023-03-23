package com.yunseojin.MyLittleHomepage.v2.domain.board.command.validation;

import com.yunseojin.MyLittleHomepage.v2.domain.board.command.exception.BoardErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.domain.board.command.exception.BoardException;
import com.yunseojin.MyLittleHomepage.v2.domain.board.query.model.QueriedBoard;
import com.yunseojin.MyLittleHomepage.v2.domain.board.query.repository.QueriedBoardRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardValidator {

    private final QueriedBoardRepository repository;


    public void validateName(QueriedBoard board, String exclude) {
        if (duplicatedName(board.getName(), exclude)) {
            throw new BoardException(BoardErrorMessage.NAME_DUPLICATE_EXCEPTION);
        }
    }

    private boolean duplicatedName(String name, String exclude) {
        return notEquals(name, exclude) && repository.existsByName(name);
    }

    private boolean notEquals(String str1, String str2) {
        return !Objects.equals(str1, str2);
    }
}
