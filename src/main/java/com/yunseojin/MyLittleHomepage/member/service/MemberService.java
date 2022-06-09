package com.yunseojin.MyLittleHomepage.member.service;

import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.dto.MemberResponse;

import java.util.Map;

public interface MemberService {
    MemberResponse register(MemberRequest memberRequest);

    MemberResponse modify(MemberRequest memberRequest);

    void delete(MemberRequest memberRequest);

    MemberResponse login(MemberRequest memberRequest);

    void logout();
}
