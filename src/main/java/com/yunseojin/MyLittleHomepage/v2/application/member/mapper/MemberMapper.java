package com.yunseojin.MyLittleHomepage.v2.application.member.mapper;

import com.yunseojin.MyLittleHomepage.v2.application.member.dto.MemberCommand;
import com.yunseojin.MyLittleHomepage.v2.application.member.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.v2.domain.member.command.model.Member;
import com.yunseojin.MyLittleHomepage.v2.domain.member.query.model.QueriedMember;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface MemberMapper {

    QueriedMember from(MemberCommand command);

    MemberResponse toResponse(Member member);

    MemberResponse toResponse(QueriedMember member);
}
