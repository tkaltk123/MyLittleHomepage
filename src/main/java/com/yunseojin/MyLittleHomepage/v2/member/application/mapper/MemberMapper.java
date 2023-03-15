package com.yunseojin.MyLittleHomepage.v2.member.application.mapper;

import com.yunseojin.MyLittleHomepage.v2.member.application.dto.MemberCommand;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.v2.member.domain.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface MemberMapper {

    @Mapping(target = "role", ignore = true)
    Member from(MemberCommand command);

    MemberResponse toResponse(Member member);
}
