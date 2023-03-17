package com.yunseojin.MyLittleHomepage.v2.post.domain.vo;

import com.yunseojin.MyLittleHomepage.v2.post.domain.enums.PostSearchType;
import lombok.Value;

@Value
public class PostSearchVo {

    Long boardId;

    PostSearchType postSearchType;

    String keyword;
}
