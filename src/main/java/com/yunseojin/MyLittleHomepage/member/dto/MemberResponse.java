package com.yunseojin.MyLittleHomepage.member.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yunseojin.MyLittleHomepage.etc.enums.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {

    private String loginId;
    private String nickname;
    private MemberType memberType;
    private LocalDateTime createdAt;
}
