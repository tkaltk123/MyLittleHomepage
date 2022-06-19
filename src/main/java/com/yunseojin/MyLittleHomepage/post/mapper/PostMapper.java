package com.yunseojin.MyLittleHomepage.post.mapper;

import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.dto.PostResponse;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "Spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "boardId", source = "board.id")
    @Mapping(target = "writerId", source = "writer.id")
    @Mapping(target = "hashtags", expression = "java(post.getStringHashtags())")
    PostResponse toPostResponse(PostEntity post);

    PostEntity toPostEntity(PostRequest postRequest);

    PostRequest toPostRequest(PostResponse postResponse);
}
