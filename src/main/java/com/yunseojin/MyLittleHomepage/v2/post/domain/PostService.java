package com.yunseojin.MyLittleHomepage.v2.post.domain;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.validation.Create;
import com.yunseojin.MyLittleHomepage.v2.contract.domain.validation.Update;
import com.yunseojin.MyLittleHomepage.v2.member.Member;
import com.yunseojin.MyLittleHomepage.v2.post.domain.exception.PostErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.post.domain.exception.PostException;
import com.yunseojin.MyLittleHomepage.v2.post.domain.validation.board.BoardExists;
import com.yunseojin.MyLittleHomepage.v2.post.domain.validation.post.PostExists;
import java.util.Objects;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostService {

    @BoardExists
    @Validated(Create.class)
    public Post create(@Valid PostVo postvo) {
        return new Post(postvo);
    }

    @Validated(Update.class)
    public Post update(@PostExists Post post, @Valid PostVo postVo) {

        validateWriter(post.getWriterId(), postVo.getWriterId());
        post.update(postVo);
        return post;
    }

    private void validateWriter(Long writerId, Long memberId) {

        if (!Objects.equals(writerId, memberId)) {
            throw new PostException(PostErrorMessage.NOT_WRITER_EXCEPTION);
        }
    }

    public void delete(@PostExists Post post, Member member) {

        validateWriter(post.getWriterId(), member.getId());
        post.delete();
    }
}
