package com.yunseojin.MyLittleHomepage.application.member.mapper;

import com.yunseojin.MyLittleHomepage.domain.member.query.model.QueriedMember;
import com.yunseojin.MyLittleHomepage.application.member.dto.MemberCommand;
import com.yunseojin.MyLittleHomepage.application.member.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.domain.member.command.model.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface MemberMapper {

    QueriedMember from(MemberCommand command);

    MemberResponse toResponse(Member member);

    MemberResponse toResponse(QueriedMember member);
}
