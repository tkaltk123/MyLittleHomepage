package com.yunseojin.MyLittleHomepage.v2.post.application.mapper;

import com.yunseojin.MyLittleHomepage.v2.post.application.dto.PostCommand;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.v2.post.domain.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface PostMapperV2 {

    PostResponse toResponse(Post post, String boardName);

    @Mapping(target = "writerName", ignore = true)
    @Mapping(target = "postCount", ignore = true)
    Post from(PostCommand command);

}
