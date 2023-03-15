package com.yunseojin.MyLittleHomepage.v2.member.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Getter
@NoArgsConstructor
@SuperBuilder
public abstract class MemberAuthOperation extends AuthOperation {

    private String currentPassword;
}
