package com.yunseojin.MyLittleHomepage.v2.application.post.service.query;

import com.yunseojin.MyLittleHomepage.v2.application.contract.service.QueryHandler;
import com.yunseojin.MyLittleHomepage.v2.application.post.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.v2.application.post.dto.query.GetPostByIdQuery;
import com.yunseojin.MyLittleHomepage.v2.application.post.mapper.PostMapperV2;
import com.yunseojin.MyLittleHomepage.v2.domain.post.query.repository.QueriedPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class GetPostByIdQueryHandler implements QueryHandler<GetPostByIdQuery, PostResponse> {

    private final QueriedPostRepository repository;

    private final PostMapperV2 mapper;

    @Override
    public PostResponse handle(GetPostByIdQuery query) {

        var post = repository.getById(query.getPostId());
        return mapper.toResponse(post);
    }
}
