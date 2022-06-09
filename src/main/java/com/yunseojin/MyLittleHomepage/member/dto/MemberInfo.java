package com.yunseojin.MyLittleHomepage.member.dto;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.CreateRepeatException;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@ToString
public class MemberInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String loginId;
    private String nickname;
    private final Set<Long> viewPosts = new HashSet<>();
    private LocalDateTime lastCreatedPostAt;
    private LocalDateTime lastCreatedCommentAt;

    public void clear() {
        id = null;
        loginId = null;
        nickname = null;
        lastCreatedPostAt = null;
        lastCreatedCommentAt = null;
    }

    public void setMember(MemberEntity member) {
        id = member.getId();
        loginId = member.getLoginId();
        nickname = member.getNickname();
    }

    public boolean viewPost(Long postId) {
        if (viewPosts.contains(postId))
            return false;
        viewPosts.add(postId);
        return true;
    }

    public void createPost() {
        var now = LocalDateTime.now();
        long diff = 0;
        if (lastCreatedPostAt != null && (diff = Duration.between(lastCreatedPostAt, now).getSeconds()) < 10L)
            throw new CreateRepeatException(10 - (int) diff, ErrorMessage.POST_REPEAT_EXCEPTION);
        lastCreatedPostAt = now;
    }

    public void createComment() {
        var now = LocalDateTime.now();
        long diff = 0;
        if (lastCreatedCommentAt != null && (diff = Duration.between(lastCreatedCommentAt, now).getSeconds()) < 10L)
            throw new CreateRepeatException(10 - (int) diff, ErrorMessage.COMMENT_REPEAT_EXCEPTION);
        lastCreatedCommentAt = now;
    }
}
