package com.yunseojin.MyLittleHomepage.member.service;

import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;

public interface InternalMemberService {

    MemberEntity getMemberById(Long memberId);

    MemberEntity getMemberByLoginId(String loginId);
}
