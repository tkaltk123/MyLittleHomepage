package com.yunseojin.MyLittleHomepage.v2.domain.auth.service;

import com.yunseojin.MyLittleHomepage.v2.domain.member.query.model.QueriedMember;

public interface SecurityContextAccessor {

    QueriedMember getMember();
}
