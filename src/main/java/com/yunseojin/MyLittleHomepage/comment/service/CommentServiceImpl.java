package com.yunseojin.MyLittleHomepage.comment.service;

import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentResponse;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentCount;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.comment.mapper.CommentMapper;
import com.yunseojin.MyLittleHomepage.comment.repository.CommentRepository;
import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
        var member = memberRepository.getMember(memberInfo.getId());
        var post = postRepository.getPost(postId);
        var comment = CommentMapper.INSTANCE.toCommentEntity(commentRequest);
        var parent = commentRepository.getComment(commentRequest.getParentId());
        if (parent != null && parent.getParent() != null)
            throw new BadRequestException(ErrorMessage.COMMENT_PARENT_EXCEPTION);

        comment.setWriter(member);
        comment.setWriterName(member.getNickname());
        comment.setPost(post);
        comment.setCommentCount(new CommentCount());
        comment.setParent(parent);

        memberInfo.createComment();
        post.increaseCommentCount();
        commentRepository.save(comment);

        return CommentMapper.INSTANCE.toPostResponse(comment);
    }

    @Login
    @Override
    public CommentResponse updateComment(Long commentId, CommentRequest postRequest) {
        var member = memberRepository.getMember(memberInfo.getId());
        var comment = commentRepository.getComment(commentId);

        checkCommentWriter(comment, member);
        comment.setContent(postRequest.getContent());

        return CommentMapper.INSTANCE.toPostResponse(comment);
    }

    @Login
    @Override
    public void deleteComment(Long commentId) {
        var member = memberRepository.getMember(memberInfo.getId());
        var comment = commentRepository.getComment(commentId);
        var post = comment.getPost();

        checkCommentWriter(comment, member);
        post.decreaseCommentCount();
        commentRepository.delete(comment);
        comment.setIsDeleted(1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentList(Long postId, Integer page) {
        var post = postRepository.getPost(postId);
        var pageable = PageRequest.of(page, 20);
        var commentPage = commentRepository.getRootComments(post, pageable);

        if (page != 0 && commentPage.isEmpty())
            throw new BadRequestException(ErrorMessage.PAGE_OUT_OF_RANGE_EXCEPTION);

        return CommentMapper.INSTANCE.toCommentResponseList(commentPage.toList());
    }

    //댓글의 작성자가 입력한 회원이 맞는지 확인
    private void checkCommentWriter(CommentEntity comment, MemberEntity member) {
        if (comment.getWriter() != member)
            throw new BadRequestException(ErrorMessage.NOT_WRITER_EXCEPTION);
    }

}
