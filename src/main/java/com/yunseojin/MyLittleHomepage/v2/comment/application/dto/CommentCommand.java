package com.yunseojin.MyLittleHomepage.v2.comment.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Command;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.AuthOperation;
import com.yunseojin.MyLittleHomepage.v2.member.domain.query.entity.QueriedMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class CommentCommand<A> extends AuthOperation implements Command<A> {

    @JsonIgnore
    private Long postId;

    @JsonIgnore
    private String writerName;

    private Long parentId;

    private String content;

    public Long getWriterId() {

        return getMemberId();
    }

    @JsonIgnore
    public void setMember(QueriedMember member) {
        setMemberId(member.getId());
        setWriterName(member.getNickname());
    }
}
