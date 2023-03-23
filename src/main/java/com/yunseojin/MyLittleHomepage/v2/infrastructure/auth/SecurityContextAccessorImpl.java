package com.yunseojin.MyLittleHomepage.v2.infrastructure.auth;

import com.yunseojin.MyLittleHomepage.v2.domain.auth.service.SecurityContextAccessor;
import com.yunseojin.MyLittleHomepage.v2.domain.member.query.model.QueriedMember;
import java.util.Objects;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextAccessorImpl implements SecurityContextAccessor {

    @Override
    public QueriedMember getMember() {

        var principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (Objects.nonNull(principal) && principal instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) principal).getMember();
        }

        return null;
    }
}
