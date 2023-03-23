package com.yunseojin.MyLittleHomepage.v2.comment.application.service.query;

import com.yunseojin.MyLittleHomepage.v2.comment.application.dto.query.GetCommentsQuery;
import com.yunseojin.MyLittleHomepage.v2.comment.application.dto.response.CommentResponseWithChildren;
import com.yunseojin.MyLittleHomepage.v2.comment.application.mapper.CommentMapperV2;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.query.repository.QueriedCommentRepository;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class GetCommentsQueryHandler implements
        QueryHandler<GetCommentsQuery, Page<CommentResponseWithChildren>> {

    private final QueriedCommentRepository repository;

    private final CommentMapperV2 mapper;

    @Override
    public Page<CommentResponseWithChildren> handle(GetCommentsQuery query) {

        var pageable = PageRequest.of(query.getPage(), 20);
        var posts = repository.getRootComments(query.getPostId(), pageable);
        return posts.map(mapper::toResponse);
    }
}
