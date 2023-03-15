package com.yunseojin.MyLittleHomepage.v2.post.domain.model;

import lombok.Value;

@Value
public class PostVo {

    Long boardId;
    Long writerId;
    String writerName;
    String title;
    String content;
}