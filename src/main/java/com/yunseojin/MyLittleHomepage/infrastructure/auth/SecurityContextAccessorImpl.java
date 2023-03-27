package com.yunseojin.MyLittleHomepage.infrastructure.auth;

import com.yunseojin.MyLittleHomepage.domain.auth.service.SecurityContextAccessor;
import com.yunseojin.MyLittleHomepage.domain.member.query.model.QueriedMember;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextAccessorImpl implements SecurityContextAccessor {

    @Override
    public QueriedMember getMember() {

        try {
            return ((UserDetailsImpl) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal())
                    .getMember();
        } catch (Exception ignore) {
        }
        return null;
    }
}
