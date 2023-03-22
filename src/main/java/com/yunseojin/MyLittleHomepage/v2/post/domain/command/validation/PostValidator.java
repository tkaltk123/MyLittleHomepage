package com.yunseojin.MyLittleHomepage.v2.post.domain.command.validation;

import com.yunseojin.MyLittleHomepage.v2.board.domain.command.exception.BoardErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.board.domain.command.exception.BoardException;
import com.yunseojin.MyLittleHomepage.v2.board.domain.query.repository.QueriedBoardRepository;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.exception.PostErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.exception.PostException;
import com.yunseojin.MyLittleHomepage.v2.post.domain.query.entity.QueriedPost;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostValidator {

    private final QueriedBoardRepository boardRepository;

    public void validateBoard(QueriedPost post) {

        if (!boardRepository.existsById(post.getBoardId())) {
            throw new BoardException(BoardErrorMessage.NOT_EXISTS_EXCEPTION);
        }
    }

    public void validateWriter(Long writerId, Long memberId) {

        if (!Objects.equals(writerId, memberId)) {
            throw new PostException(PostErrorMessage.NOT_WRITER_EXCEPTION);
        }
    }
}
