package com.yunseojin.MyLittleHomepage.v2.member.application.dto;

import com.yunseojin.MyLittleHomepage.etc.enums.MemberType;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MemberDto extends BaseDto {

    private String loginId;

    private String nickname;

    private MemberType memberType;
}
