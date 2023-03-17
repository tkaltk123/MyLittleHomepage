package com.yunseojin.MyLittleHomepage.v2.post.application.mapper;

import com.yunseojin.MyLittleHomepage.v2.post.application.dto.PostCommand;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.aggregete.Post;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.vo.PostVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface PostMapperV2 {

    PostResponse toResponse(Post post, String boardName);

    PostVo from(PostCommand command);
}
