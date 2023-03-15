package com.yunseojin.MyLittleHomepage.v2.post.application.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostCountResponse {

    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private Integer dislikeCount;
}
