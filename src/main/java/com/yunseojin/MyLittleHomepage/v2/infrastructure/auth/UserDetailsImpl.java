package com.yunseojin.MyLittleHomepage.v2.infrastructure.auth;

import com.yunseojin.MyLittleHomepage.v2.domain.member.query.model.QueriedMember;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final QueriedMember member;

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return List.of(GrantedMemberAuthority.of(member.getRole()));
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return String.valueOf(member.getId());
    }

    @Override
    public boolean isAccountNonExpired() {
        return !member.isDeleted();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !member.isDeleted();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !member.isDeleted();
    }

    @Override
    public boolean isEnabled() {
        return !member.isDeleted();
    }
}
