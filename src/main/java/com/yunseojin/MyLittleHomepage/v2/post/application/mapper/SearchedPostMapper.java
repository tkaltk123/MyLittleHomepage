package com.yunseojin.MyLittleHomepage.v2.post.application.mapper;

import com.yunseojin.MyLittleHomepage.v2.post.application.dto.PostSearchQuery;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.v2.post.domain.SearchedPost;
import com.yunseojin.MyLittleHomepage.v2.post.domain.vo.PostSearchVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface SearchedPostMapper {

    @Mapping(target = "boardId", source = "board.id")
    @Mapping(target = "boardName", source = "board.name")
    PostResponse toResponse(SearchedPost post);

    PostSearchVo from(PostSearchQuery query);

}
