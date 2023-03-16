package com.yunseojin.MyLittleHomepage.v2.post.domain.model;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.validation.Create;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Value;

@Value
public class PostVo {

    @NotNull(groups = {Create.class})
    Long boardId;
    @NotNull
    Long writerId;
    @NotNull
    String writerName;
    @NotNull(groups = {Create.class})
    @Size(min = 2, max = 255, message = "제목은 2~255글자 입니다")
    String title;
    String content;
}