package com.yunseojin.MyLittleHomepage.v2.auth.domain.service;

import com.yunseojin.MyLittleHomepage.v2.member.domain.query.entity.QueriedMember;

public interface SecurityContextAccessor {

    QueriedMember getMember();
}
