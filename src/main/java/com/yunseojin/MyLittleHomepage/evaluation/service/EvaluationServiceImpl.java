package com.yunseojin.MyLittleHomepage.evaluation.service;

import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.comment.repository.CommentRepository;
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
import com.yunseojin.MyLittleHomepage.util.SessionUtil;
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


    @Override
    public String likePost(Long postId) {
        SessionUtil.checkLogin(memberInfo, true);
        var member = memberRepository.getMember(memberInfo.getId());
        var post = postRepository.getPost(postId);
        var postEvaluation = postEvaluationRepository.findByPostAndWriter(post, member);
        if (postEvaluation == null) {
            postEvaluation = getPostEvaluation(member, post, EvaluationType.LIKE);
            postEvaluationRepository.save(postEvaluation);
            post.increaseLikeCount();
            return "좋아요 성공";
        }
        return applyLike(post, postEvaluation);
    }


    @Override
    public String likeComment(Long commentId) {
        SessionUtil.checkLogin(memberInfo, true);
        var member = memberRepository.getMember(memberInfo.getId());
        var comment = commentRepository.getComment(commentId);
        var commentEvaluation = commentEvaluationRepository.findByCommentAndWriter(comment, member);
        if (commentEvaluation == null) {
            commentEvaluation = getCommentEvaluation(member, comment, EvaluationType.LIKE);
            commentEvaluationRepository.save(commentEvaluation);
            comment.increaseLikeCount();
            return "좋아요 성공";
        }
        return applyLike(comment, commentEvaluation);
    }

    @Override
    public String dislikePost(Long postId) {
        SessionUtil.checkLogin(memberInfo, true);
        var member = memberRepository.getMember(memberInfo.getId());
        var post = postRepository.getPost(postId);
        var postEvaluation = postEvaluationRepository.findByPostAndWriter(post, member);
        if (postEvaluation == null) {
            postEvaluation = getPostEvaluation(member, post, EvaluationType.DISLIKE);
            postEvaluationRepository.save(postEvaluation);
            post.increaseDislikeCount();
            return "싫어요 성공";
        }
        return applyDislike(post, postEvaluation);
    }

    @Override
    public String dislikeComment(Long commentId) {
        SessionUtil.checkLogin(memberInfo, true);
        var member = memberRepository.getMember(memberInfo.getId());
        var comment = commentRepository.getComment(commentId);
        var commentEvaluation = commentEvaluationRepository.findByCommentAndWriter(comment, member);
        if (commentEvaluation == null) {
            commentEvaluation = getCommentEvaluation(member, comment, EvaluationType.DISLIKE);
            commentEvaluationRepository.save(commentEvaluation);
            comment.increaseDislikeCount();
            return "싫어요 성공";
        }
        return applyDislike(comment, commentEvaluation);
    }

    private PostEvaluationEntity getPostEvaluation(MemberEntity member, PostEntity post, EvaluationType evaluationType) {
        return PostEvaluationEntity.builder()
                .evaluationType(evaluationType)
                .post(post)
                .writer(member)
                .build();
    }

    private CommentEvaluationEntity getCommentEvaluation(MemberEntity member, CommentEntity comment, EvaluationType evaluationType) {
        return CommentEvaluationEntity.builder()
                .evaluationType(evaluationType)
                .comment(comment)
                .writer(member)
                .build();
    }

    private String applyLike(Evaluable evaluable, EvaluationEntity evaluation) {
        switch (evaluation.getEvaluationType()) {
            case DISLIKE:
                evaluable.decreaseDislikeCount();
            case NONE:
                evaluable.increaseLikeCount();
                evaluation.setEvaluationType(EvaluationType.LIKE);
                return "좋아요 성공";
            case LIKE:
                evaluation.setEvaluationType(EvaluationType.NONE);
                evaluable.decreaseLikeCount();
                return "좋아요 취소";
            default:
                return "";
        }
    }

    private String applyDislike(Evaluable evaluable, EvaluationEntity evaluation) {
        switch (evaluation.getEvaluationType()) {
            case LIKE:
                evaluable.decreaseLikeCount();
            case NONE:
                evaluable.increaseDislikeCount();
                evaluation.setEvaluationType(EvaluationType.DISLIKE);
                return "싫어요 성공";
            case DISLIKE:
                evaluation.setEvaluationType(EvaluationType.NONE);
                evaluable.decreaseDislikeCount();
                return "싫어요 취소";
            default:
                return "";
        }
    }
}
