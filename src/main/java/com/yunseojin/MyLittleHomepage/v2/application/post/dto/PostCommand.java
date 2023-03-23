package com.yunseojin.MyLittleHomepage.v2.application.post.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.v2.application.contract.dto.Command;
import com.yunseojin.MyLittleHomepage.v2.application.member.dto.MemberContainer;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class PostCommand<A> extends MemberContainer implements Command<A> {

    @JsonIgnore
    private Long boardId;

    private String title;

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
