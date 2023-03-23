package com.yunseojin.MyLittleHomepage.application.member.dto.command;

import com.yunseojin.MyLittleHomepage.application.member.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.application.member.dto.MemberCommand;
import com.yunseojin.MyLittleHomepage.domain.member.query.model.MemberContainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberCommand extends MemberContainer implements MemberCommand<MemberResponse> {

    private String username;

    private String password;

    private String nickname;

    private String currentPassword;
}
