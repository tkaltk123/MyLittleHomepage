package com.yunseojin.MyLittleHomepage.v2.application.member.service.query;

import com.yunseojin.MyLittleHomepage.v2.application.contract.service.QueryHandler;
import com.yunseojin.MyLittleHomepage.v2.application.member.dto.query.GetMemberQuery;
import com.yunseojin.MyLittleHomepage.v2.application.member.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.v2.application.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class GetMemberQueryHandler implements QueryHandler<GetMemberQuery, MemberResponse> {

    private final MemberMapper mapper;

    @Override
    public MemberResponse handle(GetMemberQuery command) {
        return mapper.toResponse(command.getMember());
    }
}