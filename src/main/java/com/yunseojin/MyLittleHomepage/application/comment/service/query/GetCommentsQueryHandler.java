package com.yunseojin.MyLittleHomepage.application.comment.service.query;

import com.yunseojin.MyLittleHomepage.application.comment.dto.query.GetCommentsQuery;
import com.yunseojin.MyLittleHomepage.application.comment.dto.response.CommentResponseWithChildren;
import com.yunseojin.MyLittleHomepage.application.comment.mapper.CommentMapper;
import com.yunseojin.MyLittleHomepage.application.contract.service.QueryHandler;
import com.yunseojin.MyLittleHomepage.domain.comment.query.repository.QueriedCommentRepository;
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

    private final CommentMapper mapper;

    @Override
    public Page<CommentResponseWithChildren> handle(GetCommentsQuery query) {

        var pageable = PageRequest.of(query.getPage(), 20);
        var posts = repository.getRootComments(query.getPostId(), pageable);
        return posts.map(mapper::toResponse);
    }
}
