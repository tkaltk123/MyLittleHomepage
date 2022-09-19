package com.yunseojin.MyLittleHomepage.board.service;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;

public interface InternalBoardService {

    BoardEntity getBoardById(Long boardId);

    void increasePostCount(BoardEntity board);

    void decreasePostCount(BoardEntity board);
}
