package com.yunseojin.MyLittleHomepage.v2.application.member.dto.command;

import com.yunseojin.MyLittleHomepage.v2.application.contract.dto.Command;
import com.yunseojin.MyLittleHomepage.v2.domain.member.query.model.MemberContainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMemberCommand extends MemberContainer implements Command<Void> {

    private String currentPassword;
}
