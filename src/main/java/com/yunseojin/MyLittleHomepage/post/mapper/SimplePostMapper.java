package com.yunseojin.MyLittleHomepage.post.mapper;

import com.yunseojin.MyLittleHomepage.post.dto.PostResponse;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface SimplePostMapper {

    SimplePostMapper INSTANCE = Mappers.getMapper(SimplePostMapper.class);

    @Mapping(target = "boardId", source = "board.id")
    @Mapping(target = "boardName", source = "board.name")
    @Mapping(target = "hashtags", ignore = true)
    @Mapping(target = "content", ignore = true)
    @Mapping(target = "viewCount" ,source = "postCount.viewCount")
    @Mapping(target = "commentCount" ,source = "postCount.commentCount")
    @Mapping(target = "likeCount" ,source = "postCount.likeCount")
    @Mapping(target = "dislikeCount" ,source = "postCount.dislikeCount")
    PostResponse toPostResponse(PostEntity post);
}
