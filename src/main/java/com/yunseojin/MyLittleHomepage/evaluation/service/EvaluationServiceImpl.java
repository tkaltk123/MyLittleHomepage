package com.yunseojin.MyLittleHomepage.evaluation.service;

import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.comment.repository.CommentRepository;
import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.etc.enums.EvaluationType;
import com.yunseojin.MyLittleHomepage.evaluation.entity.CommentEvaluationEntity;
import com.yunseojin.MyLittleHomepage.evaluation.entity.Evaluable;
import com.yunseojin.MyLittleHomepage.evaluation.entity.EvaluationEntity;
import com.yunseojin.MyLittleHomepage.evaluation.entity.PostEvaluationEntity;
import com.yunseojin.MyLittleHomepage.evaluation.repository.CommentEvaluationRepository;
import com.yunseojin.MyLittleHomepage.evaluation.repository.PostEvaluationRepository;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
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

    @Resource
    private MemberInfo memberInfo;

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostEvaluationRepository postEvaluationRepository;
    private final CommentEvaluationRepository commentEvaluationRepository;

    @Login
    @Override
    public EvaluationType likePost(Long postId) {

        return evaluatePost(postId, EvaluationType.LIKE);
    }

    @Login
    @Override
    public EvaluationType likeComment(Long commentId) {

        return evaluateComment(commentId, EvaluationType.LIKE);
    }

    @Login
    @Override
    public EvaluationType dislikePost(Long postId) {

        return evaluatePost(postId, EvaluationType.DISLIKE);
    }

    @Login
    @Override
    public EvaluationType dislikeComment(Long commentId) {

        return evaluateComment(commentId, EvaluationType.DISLIKE);
    }

    private EvaluationType evaluatePost(Long postId, EvaluationType evaluationType) {

        var member = memberRepository.getMember(memberInfo.getId());
        var post = postRepository.getPost(postId);
        var postEvaluation = postEvaluationRepository.findByPostAndWriter(post, member);

        if (postEvaluation == null)
            return createPostEvaluation(member, post, evaluationType);

        return evaluateOrCancel(post, postEvaluation, evaluationType);

    }

    private EvaluationType evaluateComment(Long commentId, EvaluationType evaluationType) {

        var member = memberRepository.getMember(memberInfo.getId());
        var comment = commentRepository.getComment(commentId);
        var commentEvaluation = commentEvaluationRepository.findByCommentAndWriter(comment, member);

        if (commentEvaluation == null)
            return createCommentEvaluation(member, comment, evaluationType);

        return evaluateOrCancel(comment, commentEvaluation, evaluationType);

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

}
