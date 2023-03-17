package com.yunseojin.MyLittleHomepage.evaluation.service;

import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.comment.service.InternalCommentService;
import com.yunseojin.MyLittleHomepage.etc.enums.EvaluationType;
import com.yunseojin.MyLittleHomepage.evaluation.entity.CommentEvaluationEntity;
import com.yunseojin.MyLittleHomepage.evaluation.entity.EvaluationEntity;
import com.yunseojin.MyLittleHomepage.evaluation.entity.PostEvaluationEntity;
import com.yunseojin.MyLittleHomepage.evaluation.repository.CommentEvaluationRepository;
import com.yunseojin.MyLittleHomepage.evaluation.repository.PostEvaluationRepository;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.post.service.InternalPostService;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.aggregate.Member;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class EvaluationServiceImpl implements EvaluationService {

    private final PostEvaluationRepository postEvaluationRepository;
    private final CommentEvaluationRepository commentEvaluationRepository;

    private final MemberRepository memberService;
    private final InternalPostService postService;
    private final InternalCommentService commentService;

    @Transactional
    @Override
    public EvaluationType likePost(Long memberId, Long postId) {

        return evaluatePost(memberId, postId, EvaluationType.LIKE);
    }

    @Transactional
    @Override
    public EvaluationType likeComment(Long memberId, Long commentId) {

        return evaluateComment(memberId, commentId, EvaluationType.LIKE);
    }

    @Transactional
    @Override
    public EvaluationType dislikePost(Long memberId, Long postId) {

        return evaluatePost(memberId, postId, EvaluationType.DISLIKE);
    }

    @Transactional
    @Override
    public EvaluationType dislikeComment(Long memberId, Long commentId) {

        return evaluateComment(memberId, commentId, EvaluationType.DISLIKE);
    }

    private EvaluationType evaluatePost(Long memberId, Long postId, EvaluationType evaluationType) {

        var member = memberService.getById(memberId);
        var post = postService.getPostById(postId);
        var optPostEvaluation = postEvaluationRepository.findByPostAndWriter(post, member);

        if (optPostEvaluation.isEmpty()) {
            return createPostEvaluation(member, post, evaluationType);
        }

        return evaluateOrCancel(post.getId(), postService, optPostEvaluation.get(), evaluationType);

    }

    private EvaluationType evaluateComment(Long memberId, Long commentId,
            EvaluationType evaluationType) {

        var member = memberService.getById(memberId);
        var comment = commentService.getCommentById(commentId);
        var optCommentEvaluation = commentEvaluationRepository.findByCommentAndWriter(comment,
                member);

        if (optCommentEvaluation.isEmpty()) {
            return createCommentEvaluation(member, comment, evaluationType);
        }

        return evaluateOrCancel(comment.getId(), commentService, optCommentEvaluation.get(),
                evaluationType);

    }

    private EvaluationType createPostEvaluation(Member member, PostEntity post,
            EvaluationType type) {

        var postEvaluation = PostEvaluationEntity.builder()
                .evaluationType(type)
                .post(post)
                .writer(member)
                .build();

        postEvaluationRepository.save(postEvaluation);
        increaseEvaluation(post.getId(), postService, type);

        return type;
    }

    private EvaluationType createCommentEvaluation(Member member, CommentEntity comment,
            EvaluationType type) {

        var commentEvaluation = CommentEvaluationEntity.builder()
                .evaluationType(type)
                .comment(comment)
                .writer(member)
                .build();

        commentEvaluationRepository.save(commentEvaluation);
        increaseEvaluation(comment.getId(), commentService, type);

        return type;
    }

    private EvaluationType evaluateOrCancel(Long evaluableId, EvaluableService evaluableService,
            EvaluationEntity evaluation, EvaluationType evaluationType) {

        var evaluatedType = evaluation.getEvaluationType();

        if (evaluatedType == evaluationType) {

            evaluation.setEvaluationType(EvaluationType.NONE);
            decreaseEvaluation(evaluableId, evaluableService, evaluationType);

            return EvaluationType.NONE;
        }

        if (evaluatedType != EvaluationType.NONE) {
            decreaseEvaluation(evaluableId, evaluableService, evaluationType.opposite());
        }

        evaluation.setEvaluationType(evaluationType);
        increaseEvaluation(evaluableId, evaluableService, evaluationType);
        return evaluationType;
    }

    private void increaseEvaluation(Long evaluableId, EvaluableService evaluableService,
            EvaluationType type) {

        switch (type) {

            case LIKE:
                log.debug("increase like:" + evaluableId);
                evaluableService.increaseLikeCount(evaluableId);
                break;

            case DISLIKE:
                log.debug("increase dislike:" + evaluableId);
                evaluableService.increaseDislikeCount(evaluableId);

            default:
                break;
        }
    }

    private void decreaseEvaluation(Long evaluableId, EvaluableService evaluableService,
            EvaluationType type) {

        switch (type) {

            case LIKE:
                log.debug("decrease like:" + evaluableId);
                evaluableService.decreaseLikeCount(evaluableId);
                break;

            case DISLIKE:
                log.debug("decrease dislike:" + evaluableId);
                evaluableService.decreaseDislikeCount(evaluableId);

            default:
                break;
        }
    }
}
