package com.yunseojin.MyLittleHomepage.v2.comment.domain.command.vo;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.command.validation.Create;
import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class CommentVo {

    @NotNull(groups = {Create.class})
    Long postId;
    @NotNull
    Long writerId;
    @NotNull
    String writerName;
    Long parentId;
    @NotNull
    String content;
}