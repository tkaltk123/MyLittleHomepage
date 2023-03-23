package com.yunseojin.MyLittleHomepage.v2.application.member.dto.response;

import com.yunseojin.MyLittleHomepage.v2.application.contract.dto.BaseResponse;
import com.yunseojin.MyLittleHomepage.v2.domain.member.vo.MemberAuthority;
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
