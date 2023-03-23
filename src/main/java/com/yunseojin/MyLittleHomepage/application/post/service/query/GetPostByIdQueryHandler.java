package com.yunseojin.MyLittleHomepage.application.post.service.query;

import com.yunseojin.MyLittleHomepage.application.contract.service.QueryHandler;
import com.yunseojin.MyLittleHomepage.application.post.dto.query.GetPostByIdQuery;
import com.yunseojin.MyLittleHomepage.application.post.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.application.post.mapper.PostMapper;
import com.yunseojin.MyLittleHomepage.domain.post.query.repository.QueriedPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class GetPostByIdQueryHandler implements QueryHandler<GetPostByIdQuery, PostResponse> {

    private final QueriedPostRepository repository;

    private final PostMapper mapper;

    @Override
    public PostResponse handle(GetPostByIdQuery query) {

        var post = repository.getById(query.getPostId());
        return mapper.toResponse(post);
    }
}
