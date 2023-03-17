package com.yunseojin.MyLittleHomepage.v2.member.application.service.query;

import com.yunseojin.MyLittleHomepage.v2.contract.application.service.QueryHandler;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.query.GetMemberQuery;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.v2.member.application.mapper.QueriedMemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class GetMemberQueryHandler implements QueryHandler<GetMemberQuery, MemberResponse> {

    private final QueriedMemberMapper mapper;

    @Override
    public MemberResponse handle(GetMemberQuery command) {
        return mapper.toResponse(command.getMember());
    }
}
