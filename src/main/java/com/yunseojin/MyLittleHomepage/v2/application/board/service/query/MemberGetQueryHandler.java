package com.yunseojin.MyLittleHomepage.v2.application.board.service.query;

import com.yunseojin.MyLittleHomepage.v2.application.board.dto.query.GetAllBoardQuery;
import com.yunseojin.MyLittleHomepage.v2.application.board.dto.response.BoardResponse;
import com.yunseojin.MyLittleHomepage.v2.application.board.mapper.BoardMapperV2;
import com.yunseojin.MyLittleHomepage.v2.domain.board.query.repository.QueriedBoardRepository;
import com.yunseojin.MyLittleHomepage.v2.application.contract.service.QueryHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberGetQueryHandler implements QueryHandler<GetAllBoardQuery, List<BoardResponse>> {

    private final QueriedBoardRepository repository;

    private final BoardMapperV2 mapper;

    @Override
    public List<BoardResponse> handle(GetAllBoardQuery command) {
        return mapper.toResponses(repository.findAllWithCount());
    }
}
