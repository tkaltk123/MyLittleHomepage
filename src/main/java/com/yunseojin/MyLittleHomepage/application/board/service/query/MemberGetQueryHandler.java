package com.yunseojin.MyLittleHomepage.application.board.service.query;

import com.yunseojin.MyLittleHomepage.application.board.dto.query.GetAllBoardQuery;
import com.yunseojin.MyLittleHomepage.application.board.dto.response.BoardResponse;
import com.yunseojin.MyLittleHomepage.application.board.mapper.BoardMapper;
import com.yunseojin.MyLittleHomepage.application.contract.service.QueryHandler;
import com.yunseojin.MyLittleHomepage.domain.board.query.repository.QueriedBoardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberGetQueryHandler implements QueryHandler<GetAllBoardQuery, List<BoardResponse>> {

    private final QueriedBoardRepository repository;

    private final BoardMapper mapper;

    @Override
    public List<BoardResponse> handle(GetAllBoardQuery command) {
        return mapper.toResponses(repository.findAllWithCount());
    }
}
