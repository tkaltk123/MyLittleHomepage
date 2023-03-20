package com.yunseojin.MyLittleHomepage.v2.member.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class MemberAuthOperation extends AuthOperation {

    private String currentPassword;
}
