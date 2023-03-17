package com.yunseojin.MyLittleHomepage.v2.post.application.mapper;

import com.yunseojin.MyLittleHomepage.v2.post.application.dto.PostSearchQuery;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.v2.post.domain.query.entity.QueriedPost;
import com.yunseojin.MyLittleHomepage.v2.post.domain.query.vo.PostSearchVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface QueriedPostMapper {

    @Mapping(target = "boardId", source = "board.id")
    @Mapping(target = "boardName", source = "board.name")
    PostResponse toResponse(QueriedPost post);

    PostSearchVo from(PostSearchQuery query);

}
