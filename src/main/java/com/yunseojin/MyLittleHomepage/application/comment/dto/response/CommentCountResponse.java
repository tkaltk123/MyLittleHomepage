package com.yunseojin.MyLittleHomepage.application.comment.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentCountResponse {

    private Integer likeCount;
    private Integer dislikeCount;
}