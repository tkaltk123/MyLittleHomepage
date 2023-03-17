package com.yunseojin.MyLittleHomepage.v2.post.application.service.query;

import com.yunseojin.MyLittleHomepage.v2.contract.application.service.QueryHandler;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.query.GetAllPostsQuery;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.v2.post.application.mapper.SearchedPostMapper;
import com.yunseojin.MyLittleHomepage.v2.post.domain.repository.SearchedPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class GetAllPostsQueryHandler implements QueryHandler<GetAllPostsQuery, Page<PostResponse>> {

    private final SearchedPostRepository repository;

    private final SearchedPostMapper mapper;

    @Override
    public Page<PostResponse> handle(GetAllPostsQuery query) {

        var posts = repository.getPosts(mapper.from(query),
                PageRequest.of(query.getPage(), 20));
        return posts.map(mapper::toResponse);
    }
}
