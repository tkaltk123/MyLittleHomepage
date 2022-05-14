package com.yunseojin.MyLittleHomepage.member.service;

import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;

import java.util.Map;

public interface MemberService {
    boolean resister(MemberRequest memberRequest);

    boolean login(MemberRequest memberRequest);

    String get();
}
