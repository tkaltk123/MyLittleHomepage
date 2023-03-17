package com.yunseojin.MyLittleHomepage.v2.post.domain.query.vo;

import lombok.Value;

@Value
public class PostSearchVo {

    Long boardId;

    PostSearchType postSearchType;

    String keyword;
}
