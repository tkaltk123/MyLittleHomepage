package com.yunseojin.MyLittleHomepage.post.service;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.board.repository.BoardRepository;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.dto.PostResponse;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.post.mapper.PostMapper;
import com.yunseojin.MyLittleHomepage.post.repository.PostRepository;
import com.yunseojin.MyLittleHomepage.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    @Resource
    private MemberInfo memberInfo;

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Override
    public PostResponse createPost(Long boardId, PostRequest postRequest) {
        SessionUtil.checkLogin(memberInfo, true);
        var _member = memberRepository.findById(memberInfo.getId());
        var _board = boardRepository.findById(boardId);

        if (_member.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_MEMBER_EXCEPTION);
        if (_board.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_BOARD_EXCEPTION);

        var member = _member.get();
        var board = _board.get();
        var post = PostEntity.builder()
                .board(board)
                .writer(member)
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .build();
        if (postRequest.getHashTags() != null)
            post.setHashtags(postRequest.getHashTags());
        board.createPost();
        postRepository.save(post);
        return PostMapper.INSTANCE.toPostResponse(post);
    }

    @Override
    public PostResponse updatePost(Long postId, PostRequest postRequest) {
        return null;
    }

    @Override
    public void deletePost(Long postId) {

    }

    @Override
    public List<PostResponse> getPostList(Long boardId) {
        return null;
    }

    @Override
    public PostResponse getPost(Long postId) {
        return null;
    }
}
