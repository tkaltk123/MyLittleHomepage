package com.yunseojin.MyLittleHomepage.v2.application.board.mapper;

import com.yunseojin.MyLittleHomepage.v2.application.board.dto.response.BoardResponse;
import com.yunseojin.MyLittleHomepage.v2.domain.board.query.model.QueriedBoard;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface BoardMapperV2 {

    List<BoardResponse> toResponses(List<QueriedBoard> boards);
}
