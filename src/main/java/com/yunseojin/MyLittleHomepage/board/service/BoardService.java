package com.yunseojin.MyLittleHomepage.board.service;

import com.yunseojin.MyLittleHomepage.board.dto.BoardResponse;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentResponse;

import java.util.List;

public interface BoardService {

    BoardResponse getBoardByName(String name);

    List<BoardResponse> getBoardList();
}
