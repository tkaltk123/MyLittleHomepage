package com.yunseojin.MyLittleHomepage.v2.member.application.mapper;

import com.yunseojin.MyLittleHomepage.v2.member.Member;
import com.yunseojin.MyLittleHomepage.v2.member.MemberVo;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.MemberCommand;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.response.MemberResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface MemberMapper {

    MemberVo from(MemberCommand command);

    MemberResponse toResponse(Member member);
}
