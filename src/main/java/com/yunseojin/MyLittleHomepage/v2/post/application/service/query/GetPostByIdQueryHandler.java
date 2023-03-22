package com.yunseojin.MyLittleHomepage.v2.post.application.service.query;

import com.yunseojin.MyLittleHomepage.v2.contract.application.service.QueryHandler;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.query.GetPostByIdQuery;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.v2.post.application.mapper.PostMapperV2;
import com.yunseojin.MyLittleHomepage.v2.post.domain.query.repository.QueriedPostRepository;
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
