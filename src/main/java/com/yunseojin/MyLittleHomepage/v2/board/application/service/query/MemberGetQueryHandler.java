package com.yunseojin.MyLittleHomepage.v2.board.application.service.query;

import com.yunseojin.MyLittleHomepage.v2.board.application.dto.query.GetAllBoardQuery;
import com.yunseojin.MyLittleHomepage.v2.board.application.dto.response.BoardResponse;
import com.yunseojin.MyLittleHomepage.v2.board.application.mapper.BoardMapperv2;
import com.yunseojin.MyLittleHomepage.v2.board.domain.repository.BoardRepositoryV2;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.QueryHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberGetQueryHandler implements QueryHandler<GetAllBoardQuery, List<BoardResponse>> {

    private final BoardRepositoryV2 boardRepository;

    private final BoardMapperv2 mapper;

    @Override
    public List<BoardResponse> handle(GetAllBoardQuery command) {
        return mapper.toResponses(boardRepository.findAllWithCount());
    }
}