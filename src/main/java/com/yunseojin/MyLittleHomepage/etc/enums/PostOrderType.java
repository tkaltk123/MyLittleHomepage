package com.yunseojin.MyLittleHomepage.etc.enums;

import lombok.Getter;

@Getter
public enum PostOrderType {
    LIKE(0,"좋아요순")
    ,COMMENT(1, "댓글순")
    ,VIEW(2, "조회순")
    ;

    Integer code;
    String displayName;

    PostOrderType(int code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
