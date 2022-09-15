package com.yunseojin.MyLittleHomepage.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberTokenDto{

    private Long id;
    private String loginId;
    private String nickname;

    public static boolean isLoggedIn(MemberTokenDto memberTokenDto) {

        return memberTokenDto != null && memberTokenDto.getId() != null;
    }
}
