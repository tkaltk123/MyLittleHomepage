package com.yunseojin.MyLittleHomepage.domain.post.vo;

import lombok.Value;

@Value
public class PostSearchVo {

    Long boardId;

    PostSearchType postSearchType;

    String keyword;
}
