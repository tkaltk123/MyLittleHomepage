package com.yunseojin.MyLittleHomepage.v2.comment.application.dto.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.v2.comment.application.dto.response.CommentResponseWithChildren;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentsQuery implements Query<Page<CommentResponseWithChildren>> {

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Long postId;

    private Integer page = 0;
}
