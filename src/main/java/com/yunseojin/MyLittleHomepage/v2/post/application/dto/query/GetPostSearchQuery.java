package com.yunseojin.MyLittleHomepage.v2.post.application.dto.query;

import com.yunseojin.MyLittleHomepage.etc.enums.PostSearchType;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.PostSearchQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetPostSearchQuery implements PostSearchQuery {

    private Long postId;

    private Long boardId;

    private PostSearchType postSearchType = PostSearchType.TITLE;

    private String keyword = "";

    private Boolean isAsc = false;

}
