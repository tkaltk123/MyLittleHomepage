package com.yunseojin.MyLittleHomepage.v2.post.application.dto;

import com.yunseojin.MyLittleHomepage.etc.enums.PostSearchType;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Query;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.response.PostResponse;
import org.springframework.data.domain.Page;


public interface PostSearchQuery extends Query<Page<PostResponse>> {

    Long getBoardId();

    PostSearchType getPostSearchType();

    String getKeyword();
}
