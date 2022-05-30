package com.yunseojin.MyLittleHomepage.comment.mapper;

import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentResponse;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.comment.repository.CommentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "postId", source = "post.id")
    CommentResponse toPostResponse(CommentEntity comment);

    List<CommentResponse> toCommentResponseList(List<CommentEntity> comments);

    CommentEntity toCommentEntity(CommentRequest comment);
}
