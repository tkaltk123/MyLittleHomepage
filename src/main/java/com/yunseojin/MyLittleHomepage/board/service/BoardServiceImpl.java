package com.yunseojin.MyLittleHomepage.board.service;

import com.yunseojin.MyLittleHomepage.board.dto.BoardResponse;
import com.yunseojin.MyLittleHomepage.board.mapper.BoardMapper;
import com.yunseojin.MyLittleHomepage.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public BoardResponse getBoardByName(String name) {

        return BoardMapper.INSTANCE.toPostResponse(boardRepository.findByName(name));
    }

    @Override
    public List<BoardResponse> getBoardList() {

        return BoardMapper.INSTANCE.toPostResponseList(boardRepository.findAll());
    }
}
