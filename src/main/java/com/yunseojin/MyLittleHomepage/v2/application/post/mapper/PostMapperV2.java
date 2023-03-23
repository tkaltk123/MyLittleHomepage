package com.yunseojin.MyLittleHomepage.v2.application.post.mapper;

import com.yunseojin.MyLittleHomepage.v2.application.post.dto.PostCommand;
import com.yunseojin.MyLittleHomepage.v2.application.post.dto.PostSearchQuery;
import com.yunseojin.MyLittleHomepage.v2.application.post.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.v2.domain.post.command.model.Post;
import com.yunseojin.MyLittleHomepage.v2.domain.post.query.model.QueriedPost;
import com.yunseojin.MyLittleHomepage.v2.domain.post.vo.PostSearchVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface PostMapperV2 {


    @Mapping(target = "board.id", source = "boardId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "postCount", ignore = true)
    QueriedPost from(PostCommand command);

    PostSearchVo from(PostSearchQuery query);

    @Mapping(target = "boardName", ignore = true)
    PostResponse toResponse(Post post);

    @Mapping(target = "boardId", source = "board.id")
    @Mapping(target = "boardName", source = "board.name")
    PostResponse toResponse(QueriedPost post);
}
