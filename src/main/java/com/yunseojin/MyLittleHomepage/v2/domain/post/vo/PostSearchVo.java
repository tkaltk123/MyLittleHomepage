package com.yunseojin.MyLittleHomepage.v2.domain.post.vo;

import lombok.Value;

@Value
public class PostSearchVo {

    Long boardId;

    PostSearchType postSearchType;

    String keyword;
}
