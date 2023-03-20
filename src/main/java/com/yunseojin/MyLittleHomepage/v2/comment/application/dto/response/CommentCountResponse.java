package com.yunseojin.MyLittleHomepage.v2.comment.application.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentCountResponse {

    private Integer likeCount;
    private Integer dislikeCount;
}
