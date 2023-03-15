package com.yunseojin.MyLittleHomepage.v2.member.application.service.query;

import com.yunseojin.MyLittleHomepage.v2.contract.application.service.QueryHandler;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.query.MemberGetQuery;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.v2.member.application.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberGetQueryHandler implements QueryHandler<MemberGetQuery, MemberResponse> {

    private final MemberMapper mapper;

    @Override
    public MemberResponse handle(MemberGetQuery command) {
        return mapper.toDto(command.getMember());
    }
}
