package com.yunseojin.MyLittleHomepage.v2.board.application.mapper;

import com.yunseojin.MyLittleHomepage.v2.board.application.dto.response.BoardResponse;
import com.yunseojin.MyLittleHomepage.v2.board.domain.query.entity.QueriedBoard;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface QueriedBoardMapper {

    List<BoardResponse> toResponses(List<QueriedBoard> boards);
}
