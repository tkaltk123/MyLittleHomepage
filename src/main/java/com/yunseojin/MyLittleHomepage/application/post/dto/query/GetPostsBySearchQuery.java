package com.yunseojin.MyLittleHomepage.application.post.dto.query;

import com.yunseojin.MyLittleHomepage.application.post.dto.PostSearchQuery;
import com.yunseojin.MyLittleHomepage.domain.post.vo.PostSearchType;
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
