package com.yunseojin.MyLittleHomepage.domain.auth.service;

import com.yunseojin.MyLittleHomepage.domain.member.query.model.QueriedMember;

public interface SecurityContextAccessor {

    QueriedMember getMember();
}
