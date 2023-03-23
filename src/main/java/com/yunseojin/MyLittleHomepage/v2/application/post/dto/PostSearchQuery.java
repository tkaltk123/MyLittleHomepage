package com.yunseojin.MyLittleHomepage.v2.application.post.dto;

import com.yunseojin.MyLittleHomepage.v2.application.contract.dto.Query;
import com.yunseojin.MyLittleHomepage.v2.application.post.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.v2.domain.post.vo.PostSearchType;
import org.springframework.data.domain.Page;


public interface PostSearchQuery extends Query<Page<PostResponse>> {

    Long getBoardId();

    PostSearchType getPostSearchType();

    String getKeyword();
}
