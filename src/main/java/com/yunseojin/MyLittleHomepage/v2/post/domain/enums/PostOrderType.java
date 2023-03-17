package com.yunseojin.MyLittleHomepage.v2.post.domain.enums;

import lombok.Getter;

@Getter
public enum PostOrderType {

    LIKE(0, "좋아요순"),
    COMMENT(1, "댓글순"),
    VIEW(2, "조회순"),
    ;

    final Integer code;

    final String displayName;

    PostOrderType(int code, String displayName) {

        this.code = code;
        this.displayName = displayName;
    }
}
