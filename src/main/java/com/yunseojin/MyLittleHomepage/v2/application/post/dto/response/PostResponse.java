package com.yunseojin.MyLittleHomepage.v2.application.post.dto.response;

import com.yunseojin.MyLittleHomepage.v2.application.contract.dto.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class PostResponse extends BaseResponse {

    private Long boardId;
    private String boardName;
    private Long writerId;
    private String writerName;
    private String title;
    private String content;
    private PostCountResponse postCount;
}
