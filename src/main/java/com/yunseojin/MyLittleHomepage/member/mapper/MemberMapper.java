package com.yunseojin.MyLittleHomepage.member.mapper;

import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.dto.MemberResponse;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.util.PasswordUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "Spring", imports = {PasswordUtil.class})
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mapping(target = "password", expression = "java(PasswordUtil.getHashedPassword(memberRequest.getPassword()))")
    MemberEntity toMemberEntity(MemberRequest memberRequest);

    MemberResponse toMemberResponse(MemberEntity memberEntity);
}
