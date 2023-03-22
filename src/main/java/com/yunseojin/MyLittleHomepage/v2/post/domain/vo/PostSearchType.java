package com.yunseojin.MyLittleHomepage.v2.post.domain.vo;

import lombok.Getter;

@Getter
public enum PostSearchType {

    TITLE(0, "제목"),
    CONTENT(1, "내용"),
    WRITER(2, "작성자"),
    //,TAG(3, "태그")
    ;

    final Integer code;
    final String displayName;

    PostSearchType(int code, String displayName) {

        this.code = code;
        this.displayName = displayName;
    }
}
