package com.yunseojin.MyLittleHomepage.v2.comment.application.dto.response;

import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.BaseResponse;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CommentResponseWithChildren extends BaseResponse {

    private Long postId;
    private Long writerId;
    private Long parentId;
    private String writerName;
    private String content;
    private CommentCountResponse commentCount;
    private List<CommentResponse> children;
}
