package com.yunseojin.MyLittleHomepage.v2.post.domain.validation.board;

import com.yunseojin.MyLittleHomepage.v2.board.domain.repository.BoardRepositoryV2;
import com.yunseojin.MyLittleHomepage.v2.post.domain.model.Post;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardExistsValidator implements ConstraintValidator<BoardExists, Post> {

    private final BoardRepositoryV2 boardRepository;

    @Override
    public boolean isValid(Post post, ConstraintValidatorContext context) {
        return boardRepository.existsById(post.getBoardId());
    }
}
