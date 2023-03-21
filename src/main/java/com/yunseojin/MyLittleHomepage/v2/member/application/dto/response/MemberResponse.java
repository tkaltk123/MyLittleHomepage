package com.yunseojin.MyLittleHomepage.v2.member.application.dto.response;

import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.BaseResponse;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.vo.MemberAuthority;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MemberResponse extends BaseResponse {

    private String username;

    private String nickname;

    private MemberAuthority role;
}
