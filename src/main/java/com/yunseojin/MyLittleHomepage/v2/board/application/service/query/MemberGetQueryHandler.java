package com.yunseojin.MyLittleHomepage.v2.board.application.service.query;

import com.yunseojin.MyLittleHomepage.v2.board.application.dto.query.GetAllBoardQuery;
import com.yunseojin.MyLittleHomepage.v2.board.application.dto.response.BoardResponse;
import com.yunseojin.MyLittleHomepage.v2.board.application.mapper.QueriedBoardMapper;
import com.yunseojin.MyLittleHomepage.v2.board.domain.query.repository.QueriedBoardRepository;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.QueryHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberGetQueryHandler implements QueryHandler<GetAllBoardQuery, List<BoardResponse>> {

    private final QueriedBoardRepository repository;

    private final QueriedBoardMapper mapper;

    @Override
    public List<BoardResponse> handle(GetAllBoardQuery command) {
        return mapper.toResponses(repository.findAllWithCount());
    }
}
