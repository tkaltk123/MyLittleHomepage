package com.yunseojin.MyLittleHomepage.v2.comment.application.dto.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.v2.comment.application.dto.response.CommentResponseWithChildren;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Query;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
public class GetCommentsQuery implements Query<Page<CommentResponseWithChildren>> {

    @JsonIgnore
    @Hidden
    @Setter
    private Long postId;

    private Integer page = 0;
}
