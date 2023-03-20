package com.yunseojin.MyLittleHomepage.v2.comment.application.mapper;

import com.yunseojin.MyLittleHomepage.v2.comment.application.dto.CommentCommand;
import com.yunseojin.MyLittleHomepage.v2.comment.application.dto.response.CommentResponse;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.aggregete.Comment;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.vo.CommentVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface CommentMapperV2 {

    CommentResponse toResponse(Comment comment);

    CommentVo from(CommentCommand command);
}
