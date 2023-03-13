package com.yunseojin.MyLittleHomepage.v2.member.application.command;

import com.yunseojin.MyLittleHomepage.v2.member.application.AuthOperation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Getter
@NoArgsConstructor
@SuperBuilder
public abstract class MemberAuthOperation extends AuthOperation {

    private String currentPassword;
}
