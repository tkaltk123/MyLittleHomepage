package com.yunseojin.MyLittleHomepage.member.service;

import com.yunseojin.MyLittleHomepage.member.dto.JwtTokenResponse;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.dto.MemberResponse;

public interface MemberService {

    JwtTokenResponse register(MemberRequest memberRequest);

    String modify(Long memberId, MemberRequest memberRequest);

    void delete(Long memberId, MemberRequest memberRequest);

    JwtTokenResponse login(MemberRequest memberRequest);

    String refreshAccessToken(String accessToken, String refreshToken);
}
