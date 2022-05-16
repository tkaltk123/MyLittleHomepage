package com.yunseojin.MyLittleHomepage.member.dto;

import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

@Getter
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@ToString
public class MemberInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String loginId;

    public void clear() {
        id = null;
        loginId = null;
    }

    public void setMember(MemberEntity member) {
        id = member.getId();
        loginId = member.getLoginId();
    }
}
