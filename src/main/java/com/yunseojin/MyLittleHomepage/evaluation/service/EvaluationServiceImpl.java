package com.yunseojin.MyLittleHomepage.evaluation.service;

import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.comment.repository.CommentRepository;
import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.enums.EvaluationType;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.evaluation.entity.CommentEvaluationEntity;
import com.yunseojin.MyLittleHomepage.evaluation.entity.Evaluable;
import com.yunseojin.MyLittleHomepage.evaluation.entity.EvaluationEntity;
import com.yunseojin.MyLittleHomepage.evaluation.entity.PostEvaluationEntity;
import com.yunseojin.MyLittleHomepage.evaluation.repository.CommentEvaluationRepository;
import com.yunseojin.MyLittleHomepage.evaluation.repository.PostEvaluationRepository;
import com.yunseojin.MyLittleHomepage.member.dto.MemberTokenDto;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RequiredArgsConstructor
@Service
@Transactional
public class EvaluationServiceImpl implements EvaluationService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostEvaluationRepository postEvaluationRepository;
    private final CommentEvaluationRepository commentEvaluationRepository;

    @Login
    @Override
    public EvaluationType likePost(Long memberId, Long postId) {

        return evaluatePost(memberId, postId, EvaluationType.LIKE);
    }

    @Login
    @Override
    public EvaluationType likeComment(Long memberId, Long commentId) {

        return evaluateComment(memberId, commentId, EvaluationType.LIKE);
    }

    @Login
    @Override
    public EvaluationType dislikePost(Long memberId, Long postId) {

        return evaluatePost(memberId, postId, EvaluationType.DISLIKE);
    }

    @Login
    @Override
    public EvaluationType dislikeComment(Long memberId, Long commentId) {

        return evaluateComment(memberId, commentId, EvaluationType.DISLIKE);
    }

    private EvaluationType evaluatePost(Long memberId, Long postId, EvaluationType evaluationType) {

        var member = getMemberById(memberId);
        var post = getPostById(postId);
        var optPostEvaluation = postEvaluationRepository.findByPostAndWriter(post, member);

        if (optPostEvaluation.isEmpty())
            return createPostEvaluation(member, post, evaluationType);

        return evaluateOrCancel(post, optPostEvaluation.get(), evaluationType);

    }

    private EvaluationType evaluateComment(Long memberId, Long commentId, EvaluationType evaluationType) {

        var member = getMemberById(memberId);
        var comment = getCommentById(commentId);
        var optCommentEvaluation = commentEvaluationRepository.findByCommentAndWriter(comment, member);

        if (optCommentEvaluation.isEmpty())
            return createCommentEvaluation(member, comment, evaluationType);

        return evaluateOrCancel(comment, optCommentEvaluation.get(), evaluationType);

    }

    private EvaluationType createPostEvaluation(MemberEntity member, PostEntity post, EvaluationType type) {

        var postEvaluation = PostEvaluationEntity.builder()
                .evaluationType(type)
                .post(post)
                .writer(member)
                .build();

        postEvaluationRepository.save(postEvaluation);
        increaseEvaluation(post, type);

        return type;
    }

    private EvaluationType createCommentEvaluation(MemberEntity member, CommentEntity comment, EvaluationType type) {

        var commentEvaluation = CommentEvaluationEntity.builder()
                .evaluationType(type)
                .comment(comment)
                .writer(member)
                .build();

        commentEvaluationRepository.save(commentEvaluation);
        increaseEvaluation(comment, type);

        return type;
    }

    private EvaluationType evaluateOrCancel(Evaluable evaluable, EvaluationEntity evaluation, EvaluationType evaluationType) {

        var evaluatedType = evaluation.getEvaluationType();

        if (evaluatedType == evaluationType) {

            evaluation.setEvaluationType(EvaluationType.NONE);
            decreaseEvaluation(evaluable, evaluationType);

            return EvaluationType.NONE;
        }

        if (evaluatedType != EvaluationType.NONE)
            decreaseEvaluation(evaluable, evaluationType.opposite());

        evaluation.setEvaluationType(evaluationType);
        increaseEvaluation(evaluable, evaluationType);
        return evaluationType;
    }

    private void increaseEvaluation(Evaluable evaluable, EvaluationType type) {

        switch (type) {

            case LIKE:
                evaluable.increaseLikeCount();
                break;

            case DISLIKE:
                evaluable.increaseDislikeCount();

            default:
                break;
        }
    }

    private void decreaseEvaluation(Evaluable evaluable, EvaluationType type) {

        switch (type) {

            case LIKE:
                evaluable.decreaseLikeCount();
                break;

            case DISLIKE:
                evaluable.decreaseDislikeCount();

            default:
                break;
        }
    }

    private MemberEntity getMemberById(Long memberId) {

        var optMember = memberRepository.findById(memberId);

        if (optMember.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_MEMBER_EXCEPTION);

        return optMember.get();
    }

    private CommentEntity getCommentById(Long commentId) {

        var optComment = commentRepository.findById(commentId);

        if (optComment.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION);

        return optComment.get();
    }

    private PostEntity getPostById(Long postId) {

        var optPost = postRepository.findById(postId);

        if (optPost.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_POST_EXCEPTION);

        return optPost.get();
    }

}
