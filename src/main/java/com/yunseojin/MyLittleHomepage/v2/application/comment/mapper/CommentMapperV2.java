package com.yunseojin.MyLittleHomepage.v2.application.comment.mapper;

import com.yunseojin.MyLittleHomepage.v2.application.comment.dto.CommentCommand;
import com.yunseojin.MyLittleHomepage.v2.application.comment.dto.response.CommentResponse;
import com.yunseojin.MyLittleHomepage.v2.application.comment.dto.response.CommentResponseWithChildren;
import com.yunseojin.MyLittleHomepage.v2.domain.comment.command.model.Comment;
import com.yunseojin.MyLittleHomepage.v2.domain.comment.query.model.QueriedComment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface CommentMapperV2 {

    QueriedComment from(CommentCommand command);

    CommentResponse toResponse(Comment comment);

    CommentResponseWithChildren toResponse(QueriedComment comment);
}
