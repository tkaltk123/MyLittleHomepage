package com.yunseojin.MyLittleHomepage.application.member.dto.response;

import com.yunseojin.MyLittleHomepage.application.contract.dto.BaseResponse;
import com.yunseojin.MyLittleHomepage.domain.member.vo.MemberAuthority;
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
