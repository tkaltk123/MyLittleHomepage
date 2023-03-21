package com.yunseojin.MyLittleHomepage.v2.infrastructure.auth;

import com.yunseojin.MyLittleHomepage.v2.member.domain.command.vo.MemberAuthority;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

public class GrantedMemberAuthority implements GrantedAuthority {

    private static final Map<MemberAuthority, GrantedMemberAuthority> AUTHORITIES = initAuthorities();

    private static Map<MemberAuthority, GrantedMemberAuthority> initAuthorities() {
        return Arrays.stream(MemberAuthority.values())
                .collect(Collectors.toMap(a -> a, GrantedMemberAuthority::new));
    }

    @Getter
    private final String authority;

    public static GrantedMemberAuthority of(MemberAuthority memberAuthority) {
        return AUTHORITIES.get(memberAuthority);
    }

    private GrantedMemberAuthority(MemberAuthority memberAuthority) {
        this.authority = memberAuthority.name();
    }
}
