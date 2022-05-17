package com.yunseojin.MyLittleHomepage.post.service;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.board.repository.BoardRepository;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.dto.PostResponse;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.post.mapper.PostMapper;
import com.yunseojin.MyLittleHomepage.post.mapper.SimplePostMapper;
import com.yunseojin.MyLittleHomepage.post.repository.PostRepository;
import com.yunseojin.MyLittleHomepage.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        var member = getMemberEntity(memberInfo.getId());
        var board = getBoardEntity(boardId);
        var post = PostMapper.INSTANCE.toPostEntity(postRequest);
        post.setBoard(board);
        post.setWriter(member);
        if (postRequest.getHashTags() != null)
            post.setHashtags(postRequest.getHashTags());
        board.createPost();
        postRepository.save(post);
        return PostMapper.INSTANCE.toPostResponse(post);
    }

    @Override
    public PostResponse updatePost(Long postId, PostRequest postRequest) {
        SessionUtil.checkLogin(memberInfo, true);
        var member = getMemberEntity(memberInfo.getId());
        var post = getPostEntity(postId);
        checkPostWriter(post, member);
        post.update(postRequest);
        return PostMapper.INSTANCE.toPostResponse(post);
    }

    @Override
    public void deletePost(Long postId) {
        SessionUtil.checkLogin(memberInfo, true);
        var member = getMemberEntity(memberInfo.getId());
        var post = getPostEntity(postId);
        checkPostWriter(post, member);
        postRepository.delete(post);
    }

    @Override
    public PostResponse getPost(Long postId) {
        var post = getPostEntity(postId);
        return PostMapper.INSTANCE.toPostResponse(post);
    }

    @Override
    public List<PostResponse> getPostList(Long boardId, Integer page) {
        var pageable = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "id"));
        var board = getBoardEntity(boardId);
        var postPage = postRepository.findByBoard(board, pageable);
        if (page != 0 && postPage.isEmpty())
            throw new BadRequestException(ErrorMessage.PAGE_OUT_OF_RANGE_EXCEPTION);
        return SimplePostMapper.INSTANCE.toPostResponseList(postPage.toList());
    }

    private BoardEntity getBoardEntity(Long boardId) {
        var _board = boardRepository.findById(boardId);
        if (_board.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_BOARD_EXCEPTION);
        return _board.get();
    }

    private PostEntity getPostEntity(Long postId) {
        var _post = postRepository.findById(postId);
        if (_post.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_POST_EXCEPTION);
        return _post.get();
    }

    private MemberEntity getMemberEntity(Long memberId) {
        var _member = memberRepository.findById(memberId);
        if (_member.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_MEMBER_EXCEPTION);
        return _member.get();
    }

    //게시글의 작성자가 member가 맞는지 확인
    private void checkPostWriter(PostEntity post, MemberEntity member) {
        if (post.getWriter() != member)
            throw new BadRequestException(ErrorMessage.POST_PERMISSION_EXCEPTION);
    }

}
