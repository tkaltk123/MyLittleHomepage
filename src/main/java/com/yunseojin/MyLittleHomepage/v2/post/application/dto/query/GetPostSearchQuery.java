package com.yunseojin.MyLittleHomepage.v2.post.application.dto.query;

import com.yunseojin.MyLittleHomepage.etc.enums.PostSearchType;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Query;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.response.PostResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetPostSearchQuery implements Query<Page<PostResponse>> {

    private Long postId;

    private Long boardId;

    private PostSearchType postSearchType;

    private String keyword;

    private Boolean isAsc;

}
