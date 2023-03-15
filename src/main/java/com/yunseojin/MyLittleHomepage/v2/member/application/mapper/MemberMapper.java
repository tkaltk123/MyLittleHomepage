package com.yunseojin.MyLittleHomepage.v2.member.application.mapper;

import com.yunseojin.MyLittleHomepage.v2.member.application.dto.MemberCommand;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.v2.member.domain.model.Member;
import com.yunseojin.MyLittleHomepage.v2.member.domain.model.MemberVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface MemberMapper {

    MemberVo from(MemberCommand command);

    MemberResponse toResponse(Member member);
}
