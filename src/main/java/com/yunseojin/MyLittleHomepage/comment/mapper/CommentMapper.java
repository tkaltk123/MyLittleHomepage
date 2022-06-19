package com.yunseojin.MyLittleHomepage.comment.mapper;

import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentResponse;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "Spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "writerId", source = "writer.id")
    CommentResponse toCommentResponse(CommentEntity comment);

    CommentEntity toCommentEntity(CommentRequest comment);
}
