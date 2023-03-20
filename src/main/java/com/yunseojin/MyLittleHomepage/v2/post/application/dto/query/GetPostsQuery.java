package com.yunseojin.MyLittleHomepage.v2.post.application.dto.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.etc.enums.PostSearchType;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.PostSearchQuery;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetPostsQuery implements PostSearchQuery {

    @JsonIgnore
    @Hidden
    @Setter
    private Long boardId;

    private Integer page = 0;

    private PostSearchType postSearchType = PostSearchType.TITLE;

    private String keyword = "";

}
