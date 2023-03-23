package com.yunseojin.MyLittleHomepage.v2.application.post.dto.query;

import com.yunseojin.MyLittleHomepage.etc.enums.PostSearchType;
import com.yunseojin.MyLittleHomepage.v2.application.post.dto.PostSearchQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPostsBySearchQuery implements PostSearchQuery {

    private Long postId;

    private Long boardId;

    private PostSearchType postSearchType = PostSearchType.TITLE;

    private String keyword = "";

    private Boolean isAsc = false;

}
