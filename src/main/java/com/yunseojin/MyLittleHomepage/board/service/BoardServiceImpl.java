package com.yunseojin.MyLittleHomepage.board.service;

import com.yunseojin.MyLittleHomepage.board.dto.BoardResponse;
import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.board.mapper.BoardMapper;
import com.yunseojin.MyLittleHomepage.board.repository.BoardRepository;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public BoardResponse getBoard(String name) {

        var board = getBoardByName(name);

        return BoardMapper.INSTANCE.toPostResponse(board);
    }

    @Override
    public List<BoardResponse> getBoardList() {

        var boards = boardRepository.findAllWithCount();
        return BoardMapper.INSTANCE.toPostResponseList(boards);
    }

    private BoardEntity getBoardByName(String name) {

        var optBoard = boardRepository.findByName(name);

        if (optBoard.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_BOARD_EXCEPTION);

        return optBoard.get();
    }
}
