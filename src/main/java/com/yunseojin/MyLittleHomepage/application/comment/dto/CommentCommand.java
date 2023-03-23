package com.yunseojin.MyLittleHomepage.application.comment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.application.contract.dto.Command;
import com.yunseojin.MyLittleHomepage.domain.member.query.model.MemberContainer;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class CommentCommand<A> extends MemberContainer implements Command<A> {

    @JsonIgnore
    private Long postId;

    private Long parentId;

    private String content;

    public Long getWriterId() {

        return getMemberId();
    }

    public String getWriterName() {
        if (Objects.isNull(member)) {
            return null;
        }
        return member.getNickname();
    }
}

