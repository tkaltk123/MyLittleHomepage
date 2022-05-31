package com.yunseojin.MyLittleHomepage.board.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.board.entity.QBoardEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class DslBoardRepositoryImpl extends QuerydslRepositorySupport implements DslBoardRepository {
    public DslBoardRepositoryImpl() {
        super(BoardEntity.class);
    }

    private BoardEntity findBy(BooleanExpression predicate) {
        var b = QBoardEntity.boardEntity;
        var board = from(b)
                .select(b)
                .join(b.boardCount)
                .fetchJoin()
                .where(predicate)
                .fetchOne();
        if (board == null)
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_BOARD_EXCEPTION);
        return board;
    }

    @Override
    public BoardEntity findByName(String name) {
        return findBy(QBoardEntity.boardEntity.name.eq(name));
    }

    @Override
    public BoardEntity getBoard(Long boardId) {
        return findBy(QBoardEntity.boardEntity.id.eq(boardId));
    }
}
