package com.yunseojin.MyLittleHomepage.application.post.dto.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.application.post.dto.PostSearchQuery;
import com.yunseojin.MyLittleHomepage.domain.post.vo.PostSearchType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPostsQuery implements PostSearchQuery {

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Long boardId;

    private Integer page = 0;

    private PostSearchType postSearchType = PostSearchType.TITLE;

    private String keyword = "";

}
