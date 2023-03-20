package com.yunseojin.MyLittleHomepage.v2.post.application.dto.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.etc.enums.PostSearchType;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.PostSearchQuery;
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
