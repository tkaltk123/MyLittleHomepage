package com.yunseojin.MyLittleHomepage.domain.post.command.model;

import com.yunseojin.MyLittleHomepage.domain.contract.command.validation.Create;
import com.yunseojin.MyLittleHomepage.domain.contract.command.validation.Update;
import com.yunseojin.MyLittleHomepage.domain.post.command.validation.PostValidator;
import com.yunseojin.MyLittleHomepage.domain.post.query.model.QueriedPost;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostValidator validator;

    @Validated(Create.class)
    public Post create(@Valid QueriedPost postInfo) {
        validator.validateBoard(postInfo);
        return new Post(postInfo);
    }

    @Validated(Update.class)
    public Post update(@NotNull Post post, @Valid QueriedPost postInfo) {

        validator.validateWriter(post.getWriterId(), postInfo.getWriterId());
        return post.update(postInfo);
    }

    public void delete(@NotNull Post post, Long memberId) {

        validator.validateWriter(post.getWriterId(), memberId);
        post.delete();
    }
}
