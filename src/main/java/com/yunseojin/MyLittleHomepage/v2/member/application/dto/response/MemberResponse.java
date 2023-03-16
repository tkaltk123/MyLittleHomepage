package com.yunseojin.MyLittleHomepage.v2.member.application.dto.response;

import auth.domain.UserRole;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MemberResponse extends BaseResponse {

    private String username;

    private String nickname;

    private UserRole role;
}
