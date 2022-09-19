package com.yunseojin.MyLittleHomepage.comment.service;

import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.comment.repository.CommentRepository;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.enums.EvaluationType;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.evaluation.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InternalCommentServiceImpl implements InternalCommentService {

    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public void increaseLikeCount(Long evaluableId) {

        var comment = getCommentById(evaluableId);
        comment.getCommentCount().setLikeCount(commentRepository.getLikeCount(evaluableId) + 1);
    }

    @Transactional
    @Override
    public void decreaseLikeCount(Long evaluableId) {

        var comment = getCommentById(evaluableId);
        comment.getCommentCount().setLikeCount(commentRepository.getLikeCount(evaluableId) - 1);
    }

    @Transactional
    @Override
    public void increaseDislikeCount(Long evaluableId) {

        var comment = getCommentById(evaluableId);
        comment.getCommentCount().setDislikeCount(commentRepository.getDislikeCount(evaluableId) + 1);
    }

    @Transactional
    @Override
    public void decreaseDislikeCount(Long evaluableId) {

        var comment = getCommentById(evaluableId);
        comment.getCommentCount().setDislikeCount(commentRepository.getDislikeCount(evaluableId) - 1);
    }

    @Override
    public CommentEntity getCommentById(Long commentId) {

        var optComment = commentRepository.findById(commentId);

        if (optComment.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION);

        return optComment.get();
    }
}
