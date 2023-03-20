package com.yunseojin.MyLittleHomepage.v2.post.domain.command.validation.board;

import com.yunseojin.MyLittleHomepage.v2.board.domain.query.repository.QueriedBoardRepository;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.aggregete.Post;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardExistsValidator implements ConstraintValidator<BoardExists, Post> {

    private final QueriedBoardRepository boardRepository;

    @Override
    public boolean isValid(Post post, ConstraintValidatorContext context) {
        return boardRepository.existsById(post.getBoardId());
    }
}
