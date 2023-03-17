package com.yunseojin.MyLittleHomepage.v2.post.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Command;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.AuthOperation;
import com.yunseojin.MyLittleHomepage.v2.member.domain.query.entity.QueriedMember;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class PostCommand<A> extends AuthOperation implements Command<A> {

    @JsonIgnore
    @Hidden
    @Setter
    private Long boardId;

    @JsonIgnore
    @Hidden
    @Setter
    private String writerName;

    private String title;

    private String content;

    public Long getWriterId() {

        return getMemberId();
    }

    public void setMember(QueriedMember member) {
        setMemberId(member.getId());
        setWriterName(member.getNickname());
    }
}
