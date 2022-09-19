package com.yunseojin.MyLittleHomepage.board.service;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.board.repository.BoardRepository;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class InternalBoardServiceImpl implements InternalBoardService {

    private final BoardRepository boardRepository;

    @Override
    public BoardEntity getBoardById(Long boardId) {

        var optBoard = boardRepository.findById(boardId);

        if (optBoard.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_BOARD_EXCEPTION);

        return optBoard.get();
    }

    @Transactional
    @Override
    public void increasePostCount(BoardEntity board) {

        var postCount = boardRepository.getPostCount(board.getId());
        board.getBoardCount().setPostCount(postCount + 1);
    }

    @Transactional
    @Override
    public void decreasePostCount(BoardEntity board) {

        var postCount = boardRepository.getPostCount(board.getId());
        board.getBoardCount().setPostCount(postCount - 1);
    }
}
