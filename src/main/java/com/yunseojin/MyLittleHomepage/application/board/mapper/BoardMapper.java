package com.yunseojin.MyLittleHomepage.application.board.mapper;

import com.yunseojin.MyLittleHomepage.application.board.dto.response.BoardResponse;
import com.yunseojin.MyLittleHomepage.domain.board.query.model.QueriedBoard;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface BoardMapper {

    List<BoardResponse> toResponses(List<QueriedBoard> boards);
}
