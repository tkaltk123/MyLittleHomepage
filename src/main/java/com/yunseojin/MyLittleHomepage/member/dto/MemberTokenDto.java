package com.yunseojin.MyLittleHomepage.member.dto;

import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MemberTokenDto{

    private Long id;
    private String loginId;
    private String nickname;

    public void clear() {

    }

    public void setMember(MemberEntity member) {

    }

    public boolean viewPost(Long postId) {

        return true;
    }

    public void createPost() {

    }

    public void createComment() {

    }
}
