package com.yunseojin.MyLittleHomepage.v2.comment.application.mapper;

import com.yunseojin.MyLittleHomepage.v2.comment.application.dto.response.CommentResponseWithChildren;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.query.entity.QueriedComment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface QueriedCommentMapper {

    CommentResponseWithChildren toResponse(QueriedComment comment);
}
