package com.yunseojin.MyLittleHomepage.v2.post.domain.command.aggregete;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.command.validation.Create;
import com.yunseojin.MyLittleHomepage.v2.contract.domain.command.validation.Update;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.exception.PostErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.exception.PostException;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.validation.board.BoardExists;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.validation.post.PostExists;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.vo.PostVo;
import java.util.Objects;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
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

    public void delete(@PostExists Post post, Long memberId) {

        validateWriter(post.getWriterId(), memberId);
        post.delete();
    }
}
