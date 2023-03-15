package com.yunseojin.MyLittleHomepage.v2.board.application.mapper;

import com.yunseojin.MyLittleHomepage.v2.board.application.dto.response.BoardResponse;
import com.yunseojin.MyLittleHomepage.v2.board.domain.model.Board;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface BoardMapperv2 {

    BoardResponse toResponse(Board board);

    List<BoardResponse> toResponses(List<Board> boards);
}
