package com.yunseojin.MyLittleHomepage.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {
    private String loginId;
    private String password;
    private String nickname;
}
