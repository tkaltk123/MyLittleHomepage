package com.yunseojin.MyLittleHomepage.member.service;

import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;

import java.util.Map;

public interface MemberService {
    void resister(MemberRequest memberRequest);

    void modify(MemberRequest memberRequest);

    void delete(MemberRequest memberRequest);

    void login(MemberRequest memberRequest);

    void logout();
}
