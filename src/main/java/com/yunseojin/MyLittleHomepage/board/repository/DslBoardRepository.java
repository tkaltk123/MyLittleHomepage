package com.yunseojin.MyLittleHomepage.board.repository;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;

public interface DslBoardRepository {
    BoardEntity findByName(String name);

    BoardEntity getBoard(Long boardId);
}
