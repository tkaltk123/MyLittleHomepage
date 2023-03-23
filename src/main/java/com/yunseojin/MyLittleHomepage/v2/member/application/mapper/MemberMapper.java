package com.yunseojin.MyLittleHomepage.v2.member.application.mapper;

import com.yunseojin.MyLittleHomepage.v2.member.application.dto.MemberCommand;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.aggregate.Member;
import com.yunseojin.MyLittleHomepage.v2.member.domain.query.entity.QueriedMember;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface MemberMapper {

    QueriedMember from(MemberCommand command);

    MemberResponse toResponse(Member member);
    
    MemberResponse toResponse(QueriedMember member);
}
