package com.yunseojin.MyLittleHomepage.v2.post.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Command;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.AuthOperation;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class PostCommand<A> extends AuthOperation implements Command<A> {

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
