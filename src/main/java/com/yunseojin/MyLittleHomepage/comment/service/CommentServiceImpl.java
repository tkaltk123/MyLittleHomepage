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

        var writer = memberRepository.getMember(memberInfo.getId());
        var post = postRepository.getPost(postId);
        var parent = getParent(commentRequest.getParentId());

        var comment = CommentMapper.INSTANCE.toCommentEntity(commentRequest)
                .toBuilder()
                .writer(writer)
                .writerName(writer.getNickname())
                .post(post)
                .parent(parent)
                .build();

        createComment(post, comment);

        return CommentMapper.INSTANCE.toCommentResponse(comment);
    }

    @Login
    @Override
    public CommentResponse updateComment(CommentRequest commentRequest) {

        var writer = memberRepository.getMember(memberInfo.getId());
        var comment = commentRepository.getComment(commentRequest.getCommentId());

        checkCommentWriter(comment, writer);
        comment.update(commentRequest);

        return CommentMapper.INSTANCE.toCommentResponse(comment);
    }

    @Login
    @Override
    public void deleteComment(Long commentId) {

        var writer = memberRepository.getMember(memberInfo.getId());
        var comment = commentRepository.getComment(commentId);

        checkCommentWriter(comment, writer);
        comment.getChildren().forEach(this::deleteComment);
        deleteComment(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponse getComment(Long commentId) {

        var comment = commentRepository.getComment(commentId);
        return CommentMapper.INSTANCE.toCommentResponse(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentResponse> getCommentList(Long postId, Integer page) {

        var post = postRepository.getPost(postId);
        var pageable = PageRequest.of(page, 20);
        var commentPage = getCommentPage(post, pageable);

        return commentPage.map(CommentMapper.INSTANCE::toCommentResponse);
    }

    private CommentEntity getParent(Long parentId) {

        var parent = commentRepository.getComment(parentId);

        if (parent != null && parent.getParent() != null)
            throw new BadRequestException(ErrorMessage.COMMENT_PARENT_EXCEPTION);

        return parent;
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
