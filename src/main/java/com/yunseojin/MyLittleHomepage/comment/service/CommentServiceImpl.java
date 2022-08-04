package com.yunseojin.MyLittleHomepage.comment.service;

import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentResponse;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.comment.mapper.CommentMapper;
import com.yunseojin.MyLittleHomepage.comment.repository.CommentRepository;
import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Resource
    private MemberInfo memberInfo;

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Login
    @Override
    public CommentResponse createComment(Long postId, CommentRequest commentRequest) {

        var writer = getMemberById(memberInfo.getId());
        var post = getPostById(postId);
        var parent = getParent(commentRequest.getParentId());

        var comment = CommentMapper.INSTANCE.toCommentEntity(commentRequest)
                .toBuilder()
                .writer(writer)
                .writerName(writer.getNickname())
                .post(post)
                .build();

        comment.setParent(parent);
        createComment(post, comment);

        return CommentMapper.INSTANCE.toCommentResponse(comment);
    }

    @Login
    @Override
    public CommentResponse updateComment(CommentRequest commentRequest) {

        var writer = getMemberById(memberInfo.getId());
        var comment = getCommentById(commentRequest.getCommentId());

        checkCommentWriter(comment, writer);
        comment.update(commentRequest);

        return CommentMapper.INSTANCE.toCommentResponse(comment);
    }

    @Login
    @Override
    public void deleteComment(Long commentId) {

        var writer = getMemberById(memberInfo.getId());
        var comment = getCommentById(commentId);

        checkCommentWriter(comment, writer);
        comment.getChildren().forEach(this::deleteComment);
        deleteComment(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponse getComment(Long commentId) {

        var comment = getCommentById(commentId);

        return CommentMapper.INSTANCE.toCommentResponse(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentResponse> getCommentList(Long postId, Integer page) {

        if (page < 0)
            throw new BadRequestException(ErrorMessage.PAGE_OUT_OF_RANGE_EXCEPTION);

        var post = getPostById(postId);
        var pageable = PageRequest.of(page, 20);
        var commentPage = getCommentPage(post, pageable);

        return commentPage.map(CommentMapper.INSTANCE::toCommentResponse);
    }

    private CommentEntity getParent(Long parentId) {

        if (parentId == null)
            return null;

        var parent = getCommentById(parentId);

        if (parent.getParent() != null)
            throw new BadRequestException(ErrorMessage.COMMENT_PARENT_EXCEPTION);

        return parent;
    }

    private CommentEntity getCommentById(Long commentId) {

        var optComment = commentRepository.findById(commentId);

        if (optComment.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION);

        return optComment.get();
    }

    private MemberEntity getMemberById(Long memberId) {

        var optMember = memberRepository.findById(memberId);

        if (optMember.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_MEMBER_EXCEPTION);

        return optMember.get();
    }

    private PostEntity getPostById(Long postId) {

        var optPost = postRepository.findById(postId);

        if (optPost.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_POST_EXCEPTION);

        return optPost.get();
    }

    private void createComment(PostEntity post, CommentEntity comment) {

        memberInfo.createComment();
        post.increaseCommentCount();
        commentRepository.save(comment);
    }

    private void checkCommentWriter(CommentEntity comment, MemberEntity member) {

        if (comment.getWriter() != member)
            throw new BadRequestException(ErrorMessage.NOT_WRITER_EXCEPTION);
    }

    private void deleteComment(CommentEntity comment) {

        comment.getPost().decreaseCommentCount();
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
