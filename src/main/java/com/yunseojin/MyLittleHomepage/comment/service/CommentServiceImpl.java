package com.yunseojin.MyLittleHomepage.comment.service;

import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentResponse;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.comment.mapper.CommentMapper;
import com.yunseojin.MyLittleHomepage.comment.repository.CommentRepository;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.etc.service.RedisService;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.post.service.InternalPostService;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.aggregate.Member;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final RedisService redisService;
    private final MemberRepository memberService;
    private final InternalPostService postService;
    private final InternalCommentService commentService;


    @Transactional
    @Override
    public CommentResponse createComment(Long memberId, Long postId,
            CommentRequest commentRequest) {

        var writer = memberService.getById(memberId);
        var post = postService.getPostById(postId);
        var comment = createComment(writer, post, commentRequest);

        return CommentMapper.INSTANCE.toCommentResponse(comment);
    }

    @Transactional
    @Override
    public CommentResponse updateComment(Long memberId, CommentRequest commentRequest) {

        var writer = memberService.getById(memberId);
        var comment = commentService.getCommentById(commentRequest.getCommentId());

        checkCommentWriter(comment, writer);
        comment.update(commentRequest);

        return CommentMapper.INSTANCE.toCommentResponse(comment);
    }

    @Transactional
    @Override
    public void deleteComment(Long memberId, Long commentId) {

        var writer = memberService.getById(memberId);
        var comment = commentService.getCommentById(commentId);

        checkCommentWriter(comment, writer);
        comment.getChildren().forEach(this::deleteComment);

        deleteComment(comment);
    }

    @Override
    public CommentResponse getComment(Long commentId) {

        var comment = commentService.getCommentById(commentId);

        return CommentMapper.INSTANCE.toCommentResponse(comment);
    }

    @Override
    public Page<CommentResponse> getCommentList(Long postId, Integer page) {

        if (page < 0) {
            throw new BadRequestException(ErrorMessage.PAGE_OUT_OF_RANGE_EXCEPTION);
        }

        var post = postService.getPostById(postId);
        var pageable = PageRequest.of(page, 20);
        var commentPage = getCommentPage(post, pageable);

        return commentPage.map(CommentMapper.INSTANCE::toCommentResponse);
    }

    private CommentEntity getParent(Long parentId) {

        if (parentId == null) {
            return null;
        }

        var parent = commentService.getCommentById(parentId);

        if (parent.getParent() != null) {
            throw new BadRequestException(ErrorMessage.COMMENT_PARENT_EXCEPTION);
        }

        return parent;
    }

    private CommentEntity createComment(Member writer, PostEntity post,
            CommentRequest commentRequest) {

        var parent = getParent(commentRequest.getParentId());
        var comment = CommentMapper.INSTANCE.toCommentEntity(commentRequest)
                .toBuilder()
                .writer(writer)
                .writerName(writer.getNickname())
                .post(post)
                .build()
                .withCommentCount();

        comment.setParent(parent);

        if (!redisService.createComment(writer.getId())) {
            throw new BadRequestException(ErrorMessage.COMMENT_REPEAT_EXCEPTION);
        }

        comment = commentRepository.save(comment);
        postService.increaseCommentCount(post.getId());

        return comment;
    }

    private void checkCommentWriter(CommentEntity comment, Member member) {

        if (comment.getWriter() != member) {
            throw new BadRequestException(ErrorMessage.NOT_WRITER_EXCEPTION);
        }
    }

    private void deleteComment(CommentEntity comment) {

        postService.decreaseCommentCount(comment.getPost().getId());
        commentRepository.delete(comment);
        comment.setIsDeleted(1);
    }

    private Page<CommentEntity> getCommentPage(PostEntity post, Pageable pageable) {

        var commentPage = commentRepository.getRootComments(post, pageable);
        var totalPage = commentPage.getTotalPages();

        if (totalPage != 0 && commentPage.getNumber() >= totalPage) {

            pageable = pageable.withPage(totalPage - 1);
            commentPage = commentRepository.getRootComments(post, pageable);
        }

        return commentPage;
    }

}
