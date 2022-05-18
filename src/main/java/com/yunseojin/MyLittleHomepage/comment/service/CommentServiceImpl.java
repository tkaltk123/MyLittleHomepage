package com.yunseojin.MyLittleHomepage.comment.service;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.board.repository.BoardRepository;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentResponse;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.comment.mapper.CommentMapper;
import com.yunseojin.MyLittleHomepage.comment.repository.CommentRepository;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.post.repository.PostRepository;
import com.yunseojin.MyLittleHomepage.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private MemberInfo memberInfo;

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentResponse createComment(Long postId, CommentRequest commentRequest) {
        SessionUtil.checkLogin(memberInfo, true);
        var member = memberRepository.getMember(memberInfo.getId());
        var post = postRepository.getPost(postId);
        CommentEntity parent = null;
        if (commentRequest.getParentId() != null) {
            parent = commentRepository.getComment(commentRequest.getParentId());
            if (parent.getParent() != null)
                throw new BadRequestException(ErrorMessage.COMMENT_PARENT_EXCEPTION);
        }
        var comment = CommentMapper.INSTANCE.toCommentEntity(commentRequest);
        comment.setWriter(member);
        comment.setPost(post);
        if (parent != null) {
            comment.setParent(parent);
            parent.getChildren().add(comment);
        }
        commentRepository.save(comment);
        post.increaseCommentCount();
        return CommentMapper.INSTANCE.toPostResponse(comment);
    }

    @Override
    public CommentResponse updateComment(Long commentId, CommentRequest postRequest) {
        return null;
    }

    @Override
    public void deleteComment(Long commentId) {

    }

    @Override
    public List<CommentResponse> getCommentList(Long postId, Integer page) {

        return null;
    }


}
