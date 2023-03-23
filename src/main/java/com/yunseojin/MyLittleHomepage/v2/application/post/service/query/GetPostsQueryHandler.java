package com.yunseojin.MyLittleHomepage.v2.application.post.service.query;

import com.yunseojin.MyLittleHomepage.v2.application.contract.service.QueryHandler;
import com.yunseojin.MyLittleHomepage.v2.application.post.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.v2.application.post.dto.query.GetPostsQuery;
import com.yunseojin.MyLittleHomepage.v2.application.post.mapper.PostMapperV2;
import com.yunseojin.MyLittleHomepage.v2.domain.post.query.repository.QueriedPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class GetPostsQueryHandler implements QueryHandler<GetPostsQuery, Page<PostResponse>> {

    private final QueriedPostRepository repository;

    private final PostMapperV2 mapper;

    @Override
    public Page<PostResponse> handle(GetPostsQuery query) {

        var posts = repository.getPosts(mapper.from(query),
                PageRequest.of(query.getPage(), 20));
        return posts.map(mapper::toResponse);
    }
}
