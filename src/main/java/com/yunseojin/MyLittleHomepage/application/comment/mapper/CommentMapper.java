package com.yunseojin.MyLittleHomepage.application.comment.mapper;

import com.yunseojin.MyLittleHomepage.application.comment.dto.CommentCommand;
import com.yunseojin.MyLittleHomepage.application.comment.dto.response.CommentResponse;
import com.yunseojin.MyLittleHomepage.application.comment.dto.response.CommentResponseWithChildren;
import com.yunseojin.MyLittleHomepage.domain.comment.command.model.Comment;
import com.yunseojin.MyLittleHomepage.domain.comment.query.model.QueriedComment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface CommentMapper {

    QueriedComment from(CommentCommand command);

    CommentResponse toResponse(Comment comment);

    CommentResponseWithChildren toResponse(QueriedComment comment);
}
