package com.yunseojin.MyLittleHomepage.application.post.service.query;

import com.yunseojin.MyLittleHomepage.application.contract.service.QueryHandler;
import com.yunseojin.MyLittleHomepage.application.post.dto.query.GetPostsQuery;
import com.yunseojin.MyLittleHomepage.application.post.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.application.post.mapper.PostMapper;
import com.yunseojin.MyLittleHomepage.domain.post.query.repository.QueriedPostRepository;
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

    private final PostMapper mapper;

    @Override
    public Page<PostResponse> handle(GetPostsQuery query) {

        var posts = repository.getPosts(mapper.from(query),
                PageRequest.of(query.getPage(), 20));
        return posts.map(mapper::toResponse);
    }
}
